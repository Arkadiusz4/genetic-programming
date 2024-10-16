from threading import Thread

import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


def create_comparison_plot(file_path, save_path=None):
    df = pd.read_excel(file_path, sheet_name='Porówanie wyników')

    df.replace('#DIV/0!', np.nan, inplace=True)

    if df['Wynik funkcji'].isnull().all():
        print("Kolumna 'Wynik funkcji' jest pusta, należy ją uzupełnić danymi.")
        return

    plt.figure(figsize=(10, 6))

    plt.plot(df['X1'], df['Wartość oczekiwana'], label='Wartość oczekiwana', color='blue')

    plt.plot(df['X1'], df['Wynik funkcji'], label='Wynik funkcji', color='red', linestyle='--')

    plt.title('Porównanie: Wartość oczekiwana vs Wynik funkcji')
    plt.xlabel('X1')
    plt.ylabel('Wartość')
    plt.legend()
    plt.grid(True)

    if save_path:
        plt.savefig(save_path)
        print(f"Wykres zapisano w: {save_path}")

    plt.show()


def create_3d_comparison_plot(file_path, save_path=None):
    df = pd.read_excel(file_path, sheet_name='Porówanie wyników')

    df.replace('#DIV/0!', np.nan, inplace=True)

    if df['Wynik funkcji'].isnull().all():
        print("Kolumna 'Wynik funkcji' jest pusta lub zawiera same błędy.")
        return

    fig = plt.figure(figsize=(10, 7))
    ax = fig.add_subplot(111, projection='3d')

    ax.scatter(df['X1'], df['X2'], df['Wartość oczekiwana'], label='Wartość oczekiwana', color='blue')

    if not df['Wynik funkcji'].isnull().all():
        ax.scatter(df['X1'], df['X2'], df['Wynik funkcji'], label='Wynik funkcji', color='red')

    ax.set_title('Porównanie: Wartość oczekiwana vs Wynik funkcji')
    ax.set_xlabel('X1')
    ax.set_ylabel('X2')
    ax.set_zlabel('Wartość')
    ax.legend()

    if save_path:
        plt.savefig(save_path)
        print(f"Wykres zapisano w: {save_path}")

    plt.show()


def draw_fitness_plot(excel_file_path, save_path=None):
    df = pd.read_excel(excel_file_path, sheet_name='Fitness Data')

    # Wykres
    plt.figure(figsize=(10, 6))
    plt.plot(df['Generacja'], df['Best Fitness'], label='Best Fitness', color='blue')
    plt.plot(df['Generacja'], df['Average Fitness'], label='Average Fitness', color='red', linestyle='--')

    # Dodawanie tytułów i legendy
    plt.title('Best Fitness vs Average Fitness per Generation')
    plt.xlabel('Generacja')
    plt.ylabel('Fitness')
    plt.legend()
    plt.grid(True)

    if save_path:
        plt.savefig(save_path)
        print(f"Wykres zapisano w: {save_path}")

    plt.show()


file_path = '/Users/amika/Downloads/f6_4_data.xlsx'
comparison_plot_path = '/Users/amika/Documents/genetic-programming/assets/plots/comparison_plots/f6_4.png'
generation_plot_path = '/Users/amika/Documents/genetic-programming/assets/plots/generations_plots/f6_4.png'
#create_comparison_plot(file_path, comparison_plot_path)
#create_3d_comparison_plot(file_path, comparison_plot_path)
draw_fitness_plot(file_path, generation_plot_path)
