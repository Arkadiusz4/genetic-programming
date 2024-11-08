import json
import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from math import sin, cos, tan, exp, log, sqrt
from mpl_toolkits.mplot3d import Axes3D
import warnings


def csc(x):
    try:
        return 1 / sin(x)
    except ZeroDivisionError:
        print(f"Błąd: dzielenie przez zero w funkcji 'csc' dla x={x}.")
        return np.nan


def sec(x):
    try:
        return 1 / cos(x)
    except ZeroDivisionError:
        print(f"Błąd: dzielenie przez zero w funkcji 'sec' dla x={x}.")
        return np.nan


math_context = {
    "sin": sin,
    "cos": cos,
    "tan": tan,
    "exp": exp,
    "log": log,
    "sqrt": sqrt,
    "csc": csc,
    "sec": sec
}


def evaluate_expression(expr, x1_value, x2_value=None):
    try:
        expr = expr.replace('\n', '').strip()
        expr = expr.replace('^', '**')
        expr = expr.replace('X1', 'x1')
        expr = expr.replace('X2', 'x2')
        expr = expr.replace('Csc', 'csc')
        expr = expr.replace('Sec', 'sec')

        math_context_with_values = math_context.copy()
        math_context_with_values['x1'] = x1_value
        if x2_value is not None:
            math_context_with_values['x2'] = x2_value

        result = eval(expr, {}, math_context_with_values)

        if np.isinf(result) or np.isnan(result):
            print(f"Wynik dla x1={x1_value}, x2={x2_value} jest nieokreślony (inf lub nan).")
            return np.nan
        else:
            return result

    except ZeroDivisionError:
        print(f"Dzielenie przez zero w wyrażeniu '{expr}' z x1={x1_value}, x2={x2_value}.")
        return np.nan
    except Exception as e:
        print(f"Błąd w obliczeniu wyrażenia '{expr}' z x1={x1_value}, x2={x2_value}: {e}")
        return np.nan


def create_comparison_plot(data, save_path):
    df = pd.DataFrame(data)

    df['Wynik funkcji'] = df.apply(lambda row: evaluate_expression(row['Wynik funkcji'], row['X1']), axis=1)

    if df['Wynik funkcji'].isnull().all():
        print("Kolumna 'Wynik funkcji' jest pusta lub zawiera same błędy.")
        return

    plt.figure(figsize=(10, 6))
    plt.plot(df['X1'], df['Wartość oczekiwana'], label='Wartość oczekiwana', color='blue')
    plt.plot(df['X1'], df['Wynik funkcji'], label='Wynik funkcji', color='red', linestyle='--')
    plt.title('Porównanie: Wartość oczekiwana vs Wynik funkcji')
    plt.xlabel('X1')
    plt.ylabel('Wartość')
    plt.legend()
    plt.grid(True)
    plt.savefig(save_path)
    plt.close()
    print(f"Wykres comparison zapisano w: {save_path}")


def create_3d_comparison_plot(data, save_path):
    df = pd.DataFrame(data)

    df['Wynik funkcji'] = df.apply(lambda row: evaluate_expression(row['Wynik funkcji'], row['X1'], row['X2']), axis=1)

    if df['Wynik funkcji'].isnull().all():
        print("Kolumna 'Wynik funkcji' jest pusta lub zawiera same błędy.")
        return

    fig = plt.figure(figsize=(10, 7))
    ax = fig.add_subplot(111, projection='3d')
    ax.scatter(df['X1'], df['X2'], df['Wartość oczekiwana'], label='Wartość oczekiwana', color='blue')
    ax.scatter(df['X1'], df['X2'], df['Wynik funkcji'], label='Wynik funkcji', color='red')
    ax.set_title('Porównanie: Wartość oczekiwana vs Wynik funkcji')
    ax.set_xlabel('X1')
    ax.set_ylabel('X2')
    ax.set_zlabel('Wartość')
    ax.legend()
    plt.savefig(save_path)
    plt.close()
    print(f"Wykres 3D zapisano w: {save_path}")


def draw_fitness_plot(data, save_path):
    df = pd.DataFrame(data)
    plt.figure(figsize=(10, 6))
    plt.plot(df['Generacja'], df['Best Fitness'], label='Best Fitness', color='blue')
    plt.plot(df['Generacja'], df['Average Fitness'], label='Average Fitness', color='red', linestyle='--')
    plt.title('Best Fitness vs Average Fitness per Generation')
    plt.xlabel('Generacja')
    plt.ylabel('Fitness')
    plt.legend()
    plt.grid(True)
    plt.savefig(save_path)
    plt.close()
    print(f"Wykres fitness zapisano w: {save_path}")


def process_folder(base_folder_path):
    comparison_folder = os.path.join(base_folder_path, "comparison_plots")
    fitness_folder = os.path.join(base_folder_path, "generation_plots")
    os.makedirs(comparison_folder, exist_ok=True)
    os.makedirs(fitness_folder, exist_ok=True)

    for file_name in os.listdir(base_folder_path):
        if file_name.endswith("-values.json"):
            json_path = os.path.join(base_folder_path, file_name)
            with open(json_path, 'r') as f:
                data = json.load(f)

            plot_name = os.path.splitext(file_name)[0] + "_comparison.png"
            plot_path = os.path.join(comparison_folder, plot_name)

            if any('X2' in item for item in data[0]):
                create_3d_comparison_plot(data, plot_path)
            else:
                create_comparison_plot(data, plot_path)

        elif file_name.endswith("-fitness.json"):
            json_path = os.path.join(base_folder_path, file_name)
            with open(json_path, 'r') as f:
                data = json.load(f)

            plot_name = os.path.splitext(file_name)[0] + "_fitness.png"
            plot_path = os.path.join(fitness_folder, plot_name)
            draw_fitness_plot(data, plot_path)


base_folder_path = 'path/to/folder'
process_folder(base_folder_path)
