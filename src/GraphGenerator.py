import pandas as pd
import matplotlib.pyplot as plt

def plot_csv_data_paralelo(fileMergeSortParalelo, fileInsertionSortParalelo, fileSelectionSortParalelo, fileBubbleSortParalelo):
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
    plt.title('Gráfico dos Algoritmos Paralelos')
    plt.plot(xMergeSortParalelo, yMergeSortParalelo, color='blue', label='Merge Sort Paralelo')
    plt.plot(xInsertionSortParalelo, yInsertionSortParalelo, color='red', label='Insertion Sort Paralelo')
    plt.plot(xSelectionSortParalelo, ySelectionSortParalelo, color='green', label='Selection Sort Paralelo')
    plt.plot(xBubbleSortParalelo, yBubbleSortParalelo, color='purple', label='Bubble Sort Paralelo')
    plt.yscale("log")
    plt.legend()
    plt.grid(True)
    plt.show()

def plot_csv_data_serial(fileMergeSortSerial, fileInsertionSortSerial, fileSelectionSortSerial, fileBubbleSortSerial):
    # Lê os dados do arquivo CSV usando pandas
    dataMergeSortSerial = pd.read_csv(fileMergeSortSerial)
    dataInsertionSortSerial = pd.read_csv(fileInsertionSortSerial)
    dataSelectionSortSerial = pd.read_csv(fileSelectionSortSerial)
    dataBubbleSortSerial = pd.read_csv(fileBubbleSortSerial)

    # Extrai as colunas de interesse
    xMergeSortSerial = dataMergeSortSerial['Numeros']
    yMergeSortSerial = dataMergeSortSerial['Tempo']

    xInsertionSortSerial = dataInsertionSortSerial['Numeros']
    yInsertionSortSerial = dataInsertionSortSerial['Tempo']

    xSelectionSortSerial = dataSelectionSortSerial['Numeros']
    ySelectionSortSerial = dataSelectionSortSerial['Time']

    xBubbleSortSerial = dataBubbleSortSerial['Tamanho']
    yBubbleSortSerial = dataBubbleSortSerial['Time']

    plt.xlabel('Quantidade de Números')
    plt.ylabel('Tempo (ms)')
    plt.title('Gráfico dos Algoritmos Seriais')
    plt.plot(xMergeSortSerial, yMergeSortSerial, color='blue', label='Merge Sort Serial')
    plt.plot(xInsertionSortSerial, yInsertionSortSerial, color='red', label='Insertion Sort Serial')
    plt.plot(xSelectionSortSerial, ySelectionSortSerial, color='green', label='Selection Sort Serial')
    plt.plot(xBubbleSortSerial, yBubbleSortSerial, color='purple', label='Bubble Sort Serial')
    # plt.yscale("log")
    plt.legend()
    plt.grid(True)
    plt.show()

plot_csv_data_paralelo('mergeSortParallel.csv', 'insertionSortParallel.csv', 'selectionsort_parallel_results.csv', 'bubblesort_parallel_results.csv')

plot_csv_data_serial('mergeSortSerial.csv', 'insertionSortSerial.csv', 'selectionsort_serial_results.csv', 'bubblesort_serial_results.csv')