import numpy as np
import matplotlib.pyplot as plt
from typing import List, Tuple, Union
from math import floor

def fast_hist(arr: List[Union[int, float]],
              bins: int, rwidth=1) -> Tuple[List[int], List[float]]:
    start, end = min(arr), max(arr)
    width = (end - start) / bins
    labels = [(start + i * width) for i in range(bins + 1)]
    counts = [0 for i in range(bins)]
    for x in arr:
        ind = floor((x - start) / width)
        if ind == len(counts):
            ind = ind - 1
        counts[ind] = counts[ind] + 1

    centers = [(labels[i] + labels[i + 1]) / 2 for i in range(bins)]
    plt.bar(x=centers, height=counts, width=width * rwidth)

    return counts, labels
