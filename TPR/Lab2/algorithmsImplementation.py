from conteinerSuit import ContainerSuit as CS
from containerSize import ContainerSize
import math


class AlgorithmsImpl:
    def __init__(self, capacity=100):
        self.containerCapacity = capacity

    def theoreticalContainers(self, cargosSizes):
        return math.ceil(self.getCargosSum(cargosSizes) / self.containerCapacity)

    @staticmethod
    def getCargosSum(cargosSizes):
        return sum(cargosSizes)

    def NFA(self, sizes, sort=False, reverse=False):  # NFA
        if not sort:
            label = 'NFA(unsorted)'
            cargos = self.CargoListFromSizes(sizes)
        else:
            if reverse:
                label = 'NFA(sorted in reverse)'
            else:
                label = 'NFA(sorted)'
            cargos = self.CargoListFromSizes(sizes, sort, reverse)
        containers = CS()
        containers.addNewEmpty()
        for cargo in cargos:
            lastContainer = containers.getLastContainer()
            if lastContainer.FreeSpace() >= cargo.size:
                lastContainer.addCargo(cargo)
            else:
                containers.addNewAndPut(cargo)
        self.printContainersInfo(containers.suit, label)

    def FFA(self, sizes, sort=False, reverse=False):  # FFA
        if not sort:
            label = 'FFA(unsorted)'
            cargos = self.CargoListFromSizes(sizes)
        else:
            if reverse:
                label = 'FFA(sorted in reverse)'
            else:
                label = 'FFA(sorted)'
            cargos = self.CargoListFromSizes(sizes, sort, reverse)
        containers = CS()
        containers.addNewEmpty()
        iter = 0
        for cargo in cargos:
            lastContainer = containers.getLastContainer()
            iter += 1
            if lastContainer.FreeSpace() >= cargo.size:
                lastContainer.addCargo(cargo)
            else:
                answer = containers.getFirstSuitable(cargo)
                if answer:
                    firstSuitable = answer['container']
                    iter += answer['iter']
                    firstSuitable.addCargo(cargo)
                else:
                    iter += len(containers.suit);
                    containers.addNewAndPut(cargo)
        self.printContainersInfo(containers.suit, label)
        print('Number of iterations:', iter)

    def WFA(self, sizes, sort=False, reverse=False):  # WFA
        if not sort:
            label = 'WFA(unsorted)'
            cargos = self.CargoListFromSizes(sizes)
        else:
            if reverse:
                label = 'WFA(sorted in reverse)'
            else:
                label = 'WFA(sorted)'
            cargos = self.CargoListFromSizes(sizes, sort, reverse)
        containers = CS()
        containers.addNewEmpty()
        iter = 0
        for cargo in cargos:
            lastContainer = containers.getLastContainer()
            iter += 1
            if lastContainer.FreeSpace() >= cargo.size:
                lastContainer.addCargo(cargo)
            else:
                tempDict = containers.getMostEmpty()
                mostEmpty = tempDict['container']
                iter += tempDict['iter'] + 1
                if mostEmpty.FreeSpace() >= cargo.size:
                    mostEmpty.addCargo(cargo)
                else:
                    containers.addNewAndPut(cargo)
        self.printContainersInfo(containers.suit, label)
        print('Number of iterations:', iter)

    def BFA(self, sizes, sort=False, reverse=False):  # BFA
        if not sort:
            label = 'BFA(unsorted)'
            cargos = self.CargoListFromSizes(sizes)
        else:
            if reverse:
                label = 'BFA(sorted)'
            else:
                label = 'BFA(sorted in reverse)'
            cargos = self.CargoListFromSizes(sizes, sort, reverse)
        containers = CS()
        containers.addNewEmpty()
        iter = 0
        for cargo in cargos:
            lastContainer = containers.getLastContainer()
            iter += 1
            if lastContainer.FreeSpace() >= cargo.size:
                lastContainer.addCargo(cargo)
            else:
                tempDict = containers.getMostFullAndSuitable(cargo)
                if tempDict:
                    mostFullSuitable = tempDict['container']
                    iter += tempDict['iter'] + 1
                    if mostFullSuitable.FreeSpace() >= cargo.size:
                        mostFullSuitable.addCargo(cargo)
                    else:
                        containers.addNewAndPut(cargo)
                else:
                    iter += len(containers.suit)
                    containers.addNewAndPut(cargo)
        self.printContainersInfo(containers.suit, label)
        print('Number of iterations:', iter)

    def CargoListFromSizes(self, sizes, sort=False, reverse=False):
        if sort:
            sizes = sorted(sizes, reverse=reverse)
        cargos = []
        for size in sizes:
            newCargo = ContainerSize(size)
            cargos.append(newCargo)
        return cargos

    @staticmethod
    def printContainersInfo(suit, label=''):
        toPrint = '--------------------------' + '\n' + label + '\n' + '--------------------------' + '\n'
        #for container in suit:
        #    string = 'Container size = ' + str(container.getCargoWeight()) + '( '
        #    for cargo in container.cargos:
        #        string += str(cargo.size) + ' + '
        #    toPrint += string[0:-2] + ')\n'

        toPrint += 'Containers required: ' + str(len(suit))
        print(toPrint)
