from algorithmsImplementation import AlgorithmsImpl


def main():
    rows = [
        [90, 76, 99, 52, 31, 87, 77, 99, 57, 66, 52, 17, 41, 35, 68, 98, 84, 95, 76, 5],
        [66, 28, 54, 28, 8, 93, 78, 97, 55, 72, 74, 45, 51, 25, 97, 83, 12, 27, 82, 21],
        [93, 34, 39, 34, 21, 59, 85, 57, 54, 61, 62, 72, 41, 16, 52, 50, 62, 82, 99, 17]
    ]
    all_rows = rows[0] + rows[1] + rows[2]
    appImpl = AlgorithmsImpl()
    currentArray = all_rows
    """
    print('Min containers num for row #1: ', appImpl.theoreticalContainers(rows[0]),
          '( ' + str(appImpl.getCargosSum(rows[0])) + ' / ' + str(appImpl.containerCapacity) + ')')
    print('Min containers num for row #2: ', appImpl.theoreticalContainers(rows[1]),
          '( ' + str(appImpl.getCargosSum(rows[1])) + ' / ' + str(appImpl.containerCapacity) + ')')
    print('Min containers num for row #3: ', appImpl.theoreticalContainers(rows[2]),
          '( ' + str(appImpl.getCargosSum(rows[2])) + ' / ' + str(appImpl.containerCapacity) + ')')
    print('Min containers num for all rows: ', appImpl.theoreticalContainers(all_rows),
          '( ' + str(appImpl.getCargosSum(all_rows)) + ' / ' + str(appImpl.containerCapacity) + ')')
    """
    appImpl.NFA(currentArray, False)
    appImpl.NFA(currentArray, True, False)
    appImpl.NFA(currentArray, True, True)

    appImpl.FFA(currentArray, False)
    appImpl.FFA(currentArray, True, False)
    appImpl.FFA(currentArray, True, True)

    appImpl.WFA(currentArray, False)
    appImpl.WFA(currentArray, True, False)
    appImpl.WFA(currentArray, True, True)

    appImpl.BFA(currentArray, False)
    appImpl.BFA(currentArray, True, False)
    appImpl.BFA(currentArray, True, True)


if __name__ == '__main__':
    main() 