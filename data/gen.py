import csv
import random

random.seed(12)
output_file = "data/data.csv"
num_rows = 100000
num_input_cols = 4
num_output_cols = 4

with open(output_file, "w", newline="") as f:
    writer = csv.writer(f)

    for _ in range(num_rows):
        x1, x2, x3, x4 = (random.uniform(-1, 1) for _ in range(4))
        
        # Generate last 4 columns as a nonlinear function of first 4
        y1 = x1 * 2
        y2 = x2 + 1
        y3 = x3 - 3
        y4 = (x4 * x1) * 3
        
        # Write row
        writer.writerow([x1, x2, x3, x4, y1, y2, y3, y4])
