import yfinance as yf
import numpy as np


def generate_dat_file_from_data(data, filename, version=1, num_individuals=100, var_min=-5, var_max=5,
                                num_generations=100):
    with open(filename, 'w') as f:
        f.write(f"{version} {num_individuals} {var_min} {var_max} {num_generations}\n")

        for _, row in data.iterrows():
            f.write(f"{row['X']}\t{row['Y']}\n")


data = yf.download("USDPLN=X", period="6mo", interval="1d")
data = data['Close'].reset_index()

data['X'] = range(1, len(data) + 1)
data = data[['X', 'Close']]
data.columns = ['X', 'Y']

filename = "currency_data.dat"
generate_dat_file_from_data(data, filename)

print(f"Plik .dat został wygenerowany i zapisany jako {filename}")
