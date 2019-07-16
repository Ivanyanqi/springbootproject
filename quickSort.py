'''
    快速排序算法实现 ，
    选取中轴，左边的都比他小，右边的都比他-大，一次递归
'''
import random
import time
import sys
import io
print(sys.stdout)
#if sys.stdout.encoding != "UTF-8":
#    sys.stdout = io.TextIOWrapper(sys.stdout.buffer,encoding="UTF-8")
#    print("=====修改标准输出的编码========")
print(sys.stdout.encoding)
def quickerSort(list,low,high):

    if(low < high):
        mid = partition(list, low, high)
        quickerSort(list, low, mid - 1)
        quickerSort(list, mid + 1, high)



def partition(list,low,high):

    # 选取第一个元素作为中轴
    midVal = list[low]
    while low < high :
        #判断右边元素是否比中轴小，如果将该元素放到中轴左边
        while low<high and midVal < list[high]:
            high = high - 1
        #找到了右边比中轴小的元素,此时high位置空缺
        if low < high :
            list[low] = list[high]
            low = low + 1

        #判断左边元素是否比中轴大，如果将该元素放在中轴右边空缺的位置
        while low<high and midVal > list[low]:
            low = low + 1

        if low < high:
            list[high] = list[low]
            high = high - 1

        #循环一轮，空缺的位置则为中轴的位置
        list[low] = midVal

    return low

def main(nums):
    print("nums:{0}".format(nums))
    list = []
    for i in range(0, int(nums)):
        list.append(random.randint(0, 100000))
    start = time.time()
    quickerSort(list,0,len(list) - 1)
    end = time.time()
    print("排序的时间为：{0}".format((end-start)))
    return "score:90"

if __name__ == '__main__':
    argv = 100
    if len(sys.argv) == 2:
        argv = sys.argv[1]
    print(main(argv))




