from copy import deepcopy
import matplotlib.pyplot as plt

altRows = [
    [90, 76, 99, 52, 31, 87, 77, 99, 57, 66, 52, 17, 41, 35, 68, 98, 84, 95, 76, 5],
    [66, 28, 54, 28, 8, 93, 78, 97, 55, 72, 74, 45, 51, 25, 97, 83, 12, 27, 82, 21],
    [93, 34, 39, 34, 21, 59, 85, 57, 54, 61, 62, 72, 41, 16, 52, 50, 62, 82, 99, 17]
]


class Alternative:
    def __init__(self, id_, values):
        self.id = id_
        self.values = values
        self.dominated_by = None

    def __str__(self):
        return f'Id: {self.id} Values: {self.values} Dominated: {self.dominated_by}'

    def __repr__(self):
        return str(self)

    def __iter__(self):
        return iter(self.values)


def q1(x):
    return x // 10


def q2(x):
    return x % 10


def dominating(alternatives, compare):
    alternatives_ = deepcopy(alternatives)
    i = 0
    j = 1
    N = len(alternatives_)
    while i < N - 1:
        if alternatives_[j].dominated_by is None and compare(alternatives_[i], alternatives_[j]):
            alternatives_[j].dominated_by = alternatives_[i].id
        elif alternatives_[i].dominated_by is None and compare(alternatives_[j], alternatives_[i]):
            alternatives_[i].dominated_by = alternatives_[j].id

        if j < N - 1:
            j += 1
        else:
            i += 1
            j = i + 1
    return alternatives_


def cmp_pareto(lhs, rhs):
    return not all(map(lambda x: x[0] == x[1], zip(lhs, rhs))) \
           and all(map(lambda x: x[0] >= x[1], zip(lhs, rhs)))


def cmp_slater(lhs, rhs):
    return all(map(lambda x: x[0] > x[1], zip(lhs, rhs)))


def pareto(alternatives):
    return dominating(alternatives, cmp_pareto)


def slater(alternatives):
    return dominating(alternatives, cmp_slater)


def output(alternatives, pareto, slater):  # друк шуканих множин
    width = 3

    print('A#', end=' ')
    print(' '.join([f'{i.id:{width}}' for i in alternatives]))

    print('Q1', end=' ')
    print(' '.join([f'{i.values[0]:{width}}' for i in alternatives]))

    print('Q2', end=' ')
    print(' '.join([f'{(i.values[1]):{width}}' for i in alternatives]))

    f = lambda x: ' ' * width if x.dominated_by is None else f'{x.dominated_by:{width}}'

    print('P ', end=' ')
    print(' '.join([f(i) for i in pareto]))
    print('S ', end=' ')
    print(' '.join([f(i) for i in slater]))

    print('Pareto optimal: ', end='')
    print(' '.join([str(i.id) for i in pareto if i.dominated_by is None]))
    print('Slater optimal: ', end='')
    print(' '.join([str(i.id) for i in slater if i.dominated_by is None]))
    print()


def draw(par, sl):  # графічна інтерпретація розв’язку
    x = [i.values[0] for i in par]
    y = [i.values[1] for i in par]

    xpar = [i.values[0] for i in par if i.dominated_by is None]
    ypar = [i.values[1] for i in par if i.dominated_by is None]
    xsl = [i.values[0] for i in sl if i.dominated_by is None]
    ysl = [i.values[1] for i in sl if i.dominated_by is None]

    fig, ax = plt.subplots()
    plt.plot(x, y, 'o', color='yellow')
    plt.plot(xpar, ypar, 'o', color='blue', markersize=12)
    plt.plot(xsl, ysl, 'o', color='green', markersize=6)
    plt.show()


def main():
    bigc = [i for sublist in altRows for i in sublist]
    for row in altRows + [bigc]:
        alternatives = [(q1(i), q2(i)) for i in row]
        alternatives = [Alternative(i, val)
                        for i, val in enumerate(alternatives)]
        p = pareto(alternatives)
        s = slater(alternatives)

        output(alternatives, p, s)
        draw(p, s)


if __name__ == '__main__':
    main()