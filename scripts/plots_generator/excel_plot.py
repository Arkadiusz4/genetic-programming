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


file_path = '/Users/amika/Downloads/f3_2_data.xlsx'
save_path = '/Users/amika/Documents/genetic-programming/assets/plots/comaprison_plots/f3_2.png'
create_comparison_plot(file_path, save_path)
# create_3d_comparison_plot(file_path, save_path)
