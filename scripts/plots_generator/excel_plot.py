import json
import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from math import sin, cos, tan, exp, log, sqrt
from mpl_toolkits.mplot3d import Axes3D

math_context = {"sin": sin, "cos": cos, "tan": tan, "exp": exp, "log": log, "sqrt": sqrt}


def evaluate_expression(expr, x_value):
    try:
        return eval(expr.replace('X1', str(x_value)), {}, math_context)
    except Exception as e:
        print(f"Błąd w obliczeniu wyrażenia '{expr}' z X1={x_value}: {e}")
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


base_folder_path = '/Users/amika/Documents/projects-uni/genetic-programming/tiny-gp/tiny-gp/output/json'
process_folder(base_folder_path)
