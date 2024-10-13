import numpy as np


def fun_1(x):
    return 5 * x ** 3 - 2 * x ** 2 + 3 * x - 17


def fun_2(x):
    return np.sin(x) + np.cos(x)


def fun_3(x):
    return 2 * np.log(x + 1)


def fun_4(x, y):
    return x + 2 * y


def fun_5(x, y):
    return np.sin(x / 2) + 2 * np.cos(y)


def fun_6(x, y):
    return x ** 2 + 3 * x * y - 7 * y + 1
