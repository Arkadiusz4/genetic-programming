from fileinput import filename

import numpy as np
import pandas as pd

from functions import fun_1, fun_2, fun_3, fun_4, fun_5, fun_6, fun_7, fun_8


def generate_dat_file_single_var(func, filename, domain, version, num_individuals, var_min, var_max, num_generations,
                                 num_points):
    with open(filename, 'w') as f:
        f.write(f"{version} {num_individuals} {var_min} {var_max} {num_generations}\n")

        for x in np.linspace(domain[0], domain[1], num_points):
            f_x = func(x)
            f.write(f"{x}\t{f_x}\n")


def generate_dat_file_two_vars(func, filename, domain_x, domain_y, version, num_individuals, var_min, var_max,
                               num_generations, num_points):
    with open(filename, 'w') as f:
        f.write(f"{version} {num_individuals} {var_min} {var_max} {num_generations}\n")

        for x in np.linspace(domain_x[0], domain_x[1], num_points):
            for y in np.linspace(domain_y[0], domain_y[1], num_points):
                f_xy = func(x, y)
                f.write(f"{x}\t{y}\t{f_xy}\n")


domains_func1 = [
    (-10, 10),
    (0, 100),
    (-1, 1),
    (-1000, 1000)
]

domains_func2 = [
    (-3.14, 3.14),
    (0, 7),
    (0, 100),
    (-100, 100)
]

domains_func3 = [
    (0, 4),
    (0, 9),
    (0, 99),
    (0, 999)
]

domains_func4 = [
    (0, 1),
    (-10, 10),
    (0, 100),
    (-1000, 1000)
]

domains_func5 = [
    (-3.14, 3.14),
    (0, 7),
    (0, 100),
    (-100, 100)
]

domains_func6 = [
    (-10, 10),
    (0, 100),
    (-1, 1),
    (-1000, 1000)
]

domains_func7 = [
    (-3.14, 3.14),
]

domains_func8 = [
    (-np.pi / 2 + 0.1, np.pi / 2 - 0.1),
]

file_counter = 1
for domain in domains_func1:
    file_name = f"../../assets/data/f1_{file_counter}_data.dat"
    generate_dat_file_single_var(fun_1, file_name, domain, 1, 100, -5, 5, 100, 100)
    file_counter += 1

file_counter = 1
for domain in domains_func2:
    file_name = f"../../assets/data/f2_{file_counter}_data.dat"
    generate_dat_file_single_var(fun_2, file_name, domain, 1, 100, -5, 5, 100, 100)
    file_counter += 1

file_counter = 1
for domain in domains_func3:
    file_name = f"../../assets/data/f3_{file_counter}_data.dat"
    generate_dat_file_single_var(fun_3, file_name, domain, 1, 100, -5, 5, 100, 100)
    file_counter += 1

file_counter = 1
for domain in domains_func4:
    file_name = f"../../assets/data/f4_{file_counter}_data.dat"
    generate_dat_file_two_vars(fun_4, file_name, domain, domain, 2, 100, -5, 5, 100, 10)
    file_counter += 1

file_counter = 1
for domain in domains_func5:
    file_name = f"../../assets/data/f5_{file_counter}_data.dat"
    generate_dat_file_two_vars(fun_5, file_name, domain, domain, 2, 100, -5, 5, 100, 10)
    file_counter += 1

file_counter = 1
for domain in domains_func6:
    file_name = f"../../assets/data/f6_{file_counter}_data.dat"
    generate_dat_file_two_vars(fun_6, file_name, domain, domain, 2, 100, -5, 5, 100, 10)
    file_counter += 1

file_counter = 1
for domain in domains_func7:
    file_name = f"../../assets/data/f7_{file_counter}_data.dat"
    generate_dat_file_single_var(fun_7, file_name, domain, 1, 100, -5, 5, 100, 100)
    file_counter += 1

file_counter = 1
for domain in domains_func8:
    file_name = f"../../assets/data/f8_{file_counter}_data.dat"
    generate_dat_file_single_var(fun_8, file_name, domain, 1, 100, -5, 5, 100, 100)
    file_counter += 1
