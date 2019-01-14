package cn.ivan.client.util.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 本地命令执行器
 *
 * @author yanqi
 * @version 1.0
 */
public class LocalCommandExecutor {

    private static Logger log = LoggerFactory.getLogger(LocalCommandExecutor.class);


    private int timeout;


    /**
     * 输出结果接收线程
     */
    private ExecutorService acceptService;

    /**
     * 执行方法
     *
     * @param command 命令
     * @param env     环境变量 LANG=UTF-8
     * @param args    脚本参数
     * @return 输出结果
     */
    public Optional<String> exec(String command, String[] args, String[] env) {
        String result = null;
        if (command == null || "".equals(command)) {
            throw new IllegalArgumentException("执行命令不能为空......");
        }
        List<String> commands = new ArrayList<>();
        commands.add(command);
        if (args != null && args.length > 0) {
            commands.addAll(Arrays.asList(args));
        }
        Process process = null;
        try {
            //创建processBuilder 并将错误流重定向到标准输出
            ProcessBuilder processBuilder = new ProcessBuilder(commands)
                    .redirectErrorStream(true);
            if (env != null && env.length > 0) {
                Map<String, String> environment = processBuilder.environment();
                for (String s : env) {
                    String[] split = s.split("=");
                    if (split.length != 2) {
                        log.error("环境变量[{}}格式错误,应为key=value", s);
                    }
                    environment.put(split[0], split[1]);

                }
            }
            process = processBuilder.start();
            //接收输出结果
            InputStream inputStream = process.getInputStream();
            Future<String> future = acceptService.submit(() -> {
                StringBuilder sb = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    log.info("开始读取输出");
                    sb.append("脚本执行输出:\n [ ");
                    br.lines().map(s -> s + "\n").forEach(sb::append);
                    sb.append(" ]");
                    log.info("读取输出完毕");
                } catch (Exception e) {
                    log.error("读取输出错误", e);
                    return null;
                }
                return sb.toString();
            });
            log.info("开始执行脚本...................");
            //没有设置超时时间
            if (timeout == -1) {
                result = future.get();
            } else {
                boolean flag = process.waitFor(timeout, TimeUnit.SECONDS);
                //判断是否超时
                if (flag) {
                    result = future.get();
                } else {
                    log.info("本地方法调用执行超时");
                    //取消线程执行任务
                    future.cancel(true);
                    //销毁本地命令执行
                    // kill 进程, 关闭流
                    process.destroy();
                    log.info("本地命令销毁成功");
                }
            }
            if (result != null) {
                //正常执行结束,获取输出结果
                log.info("本地方法调用执行成功");
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            if (process != null) {
                process.destroy();
            }
            log.error("本地方法调用失败", e);
        }
        return Optional.ofNullable(result);
    }

    private LocalCommandExecutor(LocalCommandExecutorBuilder builder) {
        this.acceptService = builder.executorService;
        this.timeout = builder.timeout;
    }

    private static volatile LocalCommandExecutor localCommandExecutor;

    private static LocalCommandExecutor getInstance(LocalCommandExecutorBuilder builder) {
        if (localCommandExecutor == null) {
            synchronized (LocalCommandExecutor.class) {
                if (localCommandExecutor == null) {
                    localCommandExecutor = new LocalCommandExecutor(builder);
                }
            }
        }
        return localCommandExecutor;
    }

    /**
     * 本地方法调用执行创建
     */
    public static class LocalCommandExecutorBuilder {
        /**
         * 执行超时是时间,时间单位s，默认-1,即没有设置超时时间
         */
        private int timeout = -1;
        private ExecutorService executorService;

        public LocalCommandExecutorBuilder(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("请传入结果接收线程池");
            }
            this.executorService = executorService;
        }

        public LocalCommandExecutorBuilder setTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * 获取单例的执行对象
         *
         * @return
         */
        public LocalCommandExecutor getInstance() {
            return LocalCommandExecutor.getInstance(this);
        }
    }

}
