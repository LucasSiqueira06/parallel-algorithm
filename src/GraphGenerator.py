import pandas as pd
import matplotlib.pyplot as plt

def plot_csv_data(fileMergeSortParalelo, fileInsertionSortParalelo, fileSelectionSortParalelo, fileBubbleSortParalelo):
    # Lê os dados do arquivo CSV usando pandas
    dataMergeSortParalelo = pd.read_csv(fileMergeSortParalelo)
    dataInsertionSortParalelo = pd.read_csv(fileInsertionSortParalelo)
    dataSelectionSortParalelo = pd.read_csv(fileSelectionSortParalelo)
    dataBubbleSortParalelo = pd.read_csv(fileBubbleSortParalelo)

    # Extrai as colunas de interesse
    xMergeSortParalelo = dataMergeSortParalelo['Threads']
    yMergeSortParalelo = dataMergeSortParalelo['Tempo']

    xInsertionSortParalelo = dataInsertionSortParalelo['Threads']
    yInsertionSortParalelo = dataInsertionSortParalelo['Tempo']

    xSelectionSortParalelo = dataSelectionSortParalelo['Threads']
    ySelectionSortParalelo = dataSelectionSortParalelo['Time']

    xBubbleSortParalelo = dataBubbleSortParalelo['Threads']
    yBubbleSortParalelo = dataBubbleSortParalelo['Time']

    plt.xlabel('Threads')
    plt.ylabel('Tempo (ms)')
    plt.title('Gráfico do Merge Sort Paralelo')
    plt.plot(xMergeSortParalelo, yMergeSortParalelo, color='blue', label='Merge Sort Paralelo')
    plt.plot(xInsertionSortParalelo, yInsertionSortParalelo, color='red', label='Insertion Sort Paralelo')
    plt.plot(xSelectionSortParalelo, ySelectionSortParalelo, color='green', label='Selection Sort Paralelo')
    plt.plot(xBubbleSortParalelo, yBubbleSortParalelo, color='purple', label='Bubble Sort Paralelo')
    plt.yscale("log")
    plt.legend()
    plt.grid(True)
    plt.show()

plot_csv_data('mergeSortParallel.csv', 'insertionSortParallel.csv', 'selectionsort_parallel_results.csv', 'bubblesort_parallel_results.csv')