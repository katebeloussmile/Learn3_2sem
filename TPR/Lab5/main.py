import itertools
from collections import Counter


class Candidate:
    def __init__(self, label, ordering):
        self.label = label
        self.ordering = ordering

    def __str__(self):
        return self.label + str(self.ordering)


class InputSet:
    def __init__(self, candidates, votes):
        self.candidates = candidates
        self.votes = votes

    def __str__(self):
        return ",".join([str(elem) for elem in self.candidates])


class Voting:
    def __init__(self, inputSets):
        self.inputSets = inputSets

    def relative_majority(self):
        largest_input_set = max(self.inputSets, key=lambda x: x.votes)
        top_candidate = max(largest_input_set.candidates, key=lambda x: x.ordering)
        return top_candidate

    def alternative_voting(self):
        candidate_votes = {}
        for input_set in self.inputSets:
            for candidate in input_set.candidates:
                if candidate.label in candidate_votes:
                    candidate_votes[candidate.label] += candidate.ordering * input_set.votes
                else:
                    candidate_votes[candidate.label] = candidate.ordering * input_set.votes
        return max(candidate_votes, key=candidate_votes.get)

    def condorcet_voting(self):
        priority_list = list({})
        for input_set in self.inputSets:
            priorities = {}
            for pair in itertools.permutations(input_set.candidates, 2):
                key = str(pair[0].label + pair[1].label)
                if key[::-1] not in priorities and key not in priorities:
                    max_priority = max(pair, key=lambda x: x.ordering)
                    priorities[key] = max_priority.label
            priority_list.append(priorities)
        winners = list()
        for priorities in priority_list:
            winner, _ = Counter(priorities.values()).most_common(1)[0]
            winners.append(winner)
        list_from_set = list(set(winners))
        if len(list_from_set) != 1:
            return "Переможців немає"
        else:
            return list_from_set[0]


def main():
    candidates5 = [Candidate("a", 4), Candidate("b", 3), Candidate("c", 2), Candidate("d", 1)]
    candidates4 = [Candidate("c", 4), Candidate("a", 3), Candidate("b", 2), Candidate("d", 1)]
    candidates2 = [Candidate("b", 4), Candidate("a", 3), Candidate("c", 2), Candidate("d", 1)]

    input_set5 = InputSet(candidates5, 3)
    input_set4 = InputSet(candidates4, 4)
    input_set2 = InputSet(candidates2, 4)

    voting = Voting([input_set5, input_set4, input_set2])
    print("Переможець за правилом відносної більшості: " + voting.relative_majority().label)
    print("Переможець за правилом Кондорсе: " + voting.condorcet_voting())
    print("Переможець за методом альтернативних голосів: " + voting.alternative_voting())


if __name__ == "__main__":
    main()
