
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class EqualityOfTwoDFALanguages {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int n1= s.nextInt();  // تعداد حالت های DFA
        int m1= s.nextInt(); String extra1=s.nextLine(); // تعداد حروف الفبا
        String finalStatesIndex1 = s.nextLine();    // get the entire line after the prompt
        String[] numbers1 = finalStatesIndex1.split(" ");
        LinkedList<Integer> finalStatesIndexList1= new LinkedList<>();
        for (int i = 0; i < numbers1.length; i++) {
            finalStatesIndexList1.add(Integer.parseInt(numbers1[i]));
        }
        int[][] transitionFunction1 =new int[n1+1][m1];   // دقت
        for(int i=1; i<n1+1;i++) {
            for(int j=0; j<m1;j++) {
                transitionFunction1[i][j]=s.nextInt();
            }
        }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        int n2= s.nextInt();  // تعداد حالت های DFA
        int m2= s.nextInt(); String extra2=s.nextLine(); // تعداد حروف الفبا
        String finalStatesIndex2 = s.nextLine();    // get the entire line after the prompt
        String[] numbers2 = finalStatesIndex2.split(" ");
        LinkedList<Integer> finalStatesIndexList2= new LinkedList<>();
        for (int i = 0; i < numbers2.length; i++) {
            finalStatesIndexList2.add(Integer.parseInt(numbers2[i]));
        }
        int[][] transitionFunction2 =new int[n2+1][m2];
        for(int i=1; i<n2+1;i++) {
            for(int j=0; j<m2;j++) {
                transitionFunction2[i][j]=s.nextInt();
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        DFA unionDFA= union((n1),(n2),(m1),(m2),transitionFunction1,transitionFunction2,finalStatesIndexList1,finalStatesIndexList2,alphabet);
        DFA intersectionDFA= intersection((n1),(n2),(m1),(m2),transitionFunction1,transitionFunction2,finalStatesIndexList1,finalStatesIndexList2,alphabet);
        DFA complementDFA= complement(intersectionDFA);







        System.out.println(lastIntersection(unionDFA,complementDFA,alphabet));




    }






























































    public static DFA union(
            int n1, int n2,
            int m1, int m2,
            int[][] transitionFunction1,
            int[][] transitionFunction2,
            LinkedList<Integer> finalStatesIndexList1,
            LinkedList<Integer> finalStatesIndexList2,
            char[] alphabet
    ){
            DFA unionDFA = null;
            if (m1 == m2) {
                unionDFA = new DFA(m1, n1 * n2);
                for (int i = 1; i < n1 + 1; i++) {
                    for (int j = 1; j < n2 + 1; j++) {
                        State state = new State(i, j);
                        unionDFA.getStates().add(state);
                        if (i == 1 && j == 1) {
                            unionDFA.setStartState(state);
                        }

                        if (finalStatesIndexList1.contains(state.s1)) {
                            unionDFA.getFinalStatesIndexList().add(state);
                        } else {
                            if (finalStatesIndexList2.contains(state.s2)) {
                                unionDFA.getFinalStatesIndexList().add(state);
                            }
                        }
                    }
                }
                for (State state : unionDFA.getStates()) {
                    for (int i = 0; i < m1; i++) {
                        Transition transition = new Transition(state, alphabet[i], getState(transitionFunction1[state.s1][i], transitionFunction2[state.s2][i], unionDFA.getStates()));
                        unionDFA.getTransitionFunction().add(transition);
                    }
                }
            }
            else {
                if (m1 > m2) {
                    unionDFA = new DFA(m1, n1 * (n2 + 1));
                    for (int i = 1; i < n1 + 1; i++) {
                        for (int j = 0; j < n2 + 1; j++) {
                            State state = new State(i, j);
                            unionDFA.getStates().add(state);
                            if (i == 1 && j == 1) {
                                unionDFA.setStartState(state);
                            }

                            if (finalStatesIndexList1.contains(state.s1)) {
                                unionDFA.getFinalStatesIndexList().add(state);
                            } else {
                                if (finalStatesIndexList2.contains(state.s2)) {
                                    unionDFA.getFinalStatesIndexList().add(state);
                                }
                            }
                        }
                    }
                    for (State state : unionDFA.getStates()) {
                        for (int i = 0; i < m1; i++) {
                            Transition transition;
                            if (i >= m2) {
                                transition = new Transition(state, alphabet[i], getState(transitionFunction1[state.s1][i], 0, unionDFA.getStates()));
                            } else {
                                transition = new Transition(state, alphabet[i], getState(transitionFunction1[state.s1][i], transitionFunction2[state.s2][i], unionDFA.getStates()));
                            }
                            unionDFA.getTransitionFunction().add(transition);
                        }
                    }
                }
                if (m1 < m2) {
                    unionDFA = new DFA(m2, (n1 + 1) * n2);
                    for (int i = 0; i < n1 + 1; i++) {
                        for (int j = 1; j < n2 + 1; j++) {
                            State state = new State(i, j);
                            unionDFA.getStates().add(state);
                            if (i == 1 && j == 1) {
                                unionDFA.setStartState(state);
                            }

                            if (finalStatesIndexList1.contains(state.s1)) {
                                unionDFA.getFinalStatesIndexList().add(state);
                            } else {
                                if (finalStatesIndexList2.contains(state.s2)) {
                                    unionDFA.getFinalStatesIndexList().add(state);
                                }
                            }
                        }
                    }
                    for (State state : unionDFA.getStates()) {
                        for (int i = 0; i < m2; i++) {
                            Transition transition;
                            if (i >= m1) {
                                transition = new Transition(state, alphabet[i], getState(0, transitionFunction2[state.s2][i], unionDFA.getStates()));
                            } else {
                                transition = new Transition(state, alphabet[i], getState(transitionFunction1[state.s1][i], transitionFunction2[state.s2][i], unionDFA.getStates()));
                            }
                            unionDFA.getTransitionFunction().add(transition);
                        }
                    }
                }
            }
            return unionDFA;
    }
























    public static DFA intersection(
            int n1, int n2,
            int m1, int m2,
            int[][] transitionFunction1, int[][] transitionFunction2,
            LinkedList<Integer> finalStatesIndexList1, LinkedList<Integer> finalStatesIndexList2,
            char[] alphabet
    ){
            DFA dfa;
            dfa = new DFA(Math.min(m1, m2), n1 * n2);
            for (int i = 1; i < n1 + 1; i++) {
                for (int j = 1; j < n2 + 1; j++) {
                    State state = new State(i, j);
                    dfa.getStates().add(state);
                    if (i == 1 && j == 1) {
                        dfa.setStartState(state);
                    }

                    if (finalStatesIndexList1.contains(state.s1) && finalStatesIndexList2.contains(state.s2)) {
                        dfa.getFinalStatesIndexList().add(state);
                    }
                }
            }
            for (State state : dfa.getStates()) {
                for (int i = 0; i < Math.min(m1, m2); i++) {
                    Transition transition = new Transition(state, alphabet[i], getState(transitionFunction1[state.s1][i], transitionFunction2[state.s2][i], dfa.getStates()));
                    dfa.getTransitionFunction().add(transition);
                }
            }
            return dfa;
    }














    public static String lastIntersection(DFA dfa1, DFA dfa2, char[] alphabet){
        NewDFA newDFA = new NewDFA(
                dfa1.getNumberOfStates() * dfa2.getNumberOfStates(),
                Math.min(dfa1.getNumberOfAlphabetLetters(), dfa2.getNumberOfAlphabetLetters())
        );
        LinkedList<String> yes_strings= new LinkedList<>();
        LinkedList<LinkedList<Integer>> graph= BFS.createGraph(newDFA.getNumberOfStates()+1);
        HashMap<NewState, Integer> map = new HashMap<NewState, Integer>();
        int n=1;
            for (State state1 : dfa1.getStates()) {
                for (State state2 : dfa2.getStates()) {
                    NewState newState = new NewState(state1, state2);
                    newDFA.getStates().add(newState);
                    if (state1.s1 == 1 && state1.s2 == 1) {
                        if (state2.s1 == 1 && state2.s2 == 1) {
                            newDFA.setStartState(newState);
                        }
                    }
                    map.put(newState,n);
                    n=n+1;
                    if (dfa1.getFinalStatesIndexList().contains(newState.s1) && dfa2.getFinalStatesIndexList().contains(newState.s2)) {
                        newDFA.getFinalStatesIndexList().add(newState);
                    }
                }
            }
            for (int j = 0; j < newDFA.getNumberOfStates(); j++) {
                for (int i = 0; i < newDFA.getNumberOfAlphabetLetters(); i++) {
                    NewTransition transition = new NewTransition(
                            newDFA.getStates().get(j),
                            alphabet[i],
                            getNewState(
                                    getDestinationState(newDFA.getStates().get(j).s1, alphabet[i], dfa1.getTransitionFunction()),
                                    getDestinationState(newDFA.getStates().get(j).s2, alphabet[i], dfa2.getTransitionFunction()),
                                    newDFA.getStates()
                            )
                    );
                    BFS.addEdge(
                            graph,
                            map.get(transition.s1),
                            map.get(transition.s2)
                    );
                    newDFA.getTransitionFunction().add(transition);
                }
                if (newDFA.getFinalStatesIndexList().contains(newDFA.getStates().get(j))){
                    yes_strings.add(BFS.bfs(graph,  map.get(newDFA.getStartState()),  map.get(newDFA.getStates().get(j))));
                }
            }




        String yes_no="";
        if (yes_strings.contains("YES")){yes_no=("no");}
        else {yes_no=("yes");}


            return yes_no;
    }



















    public static DFA complement(DFA dfa){
            DFA complement;
            complement = new DFA(dfa.getNumberOfAlphabetLetters(), dfa.getNumberOfStates());
            for (State state : dfa.getStates()) {
                complement.getStates().add(state);
                if (!dfa.getFinalStatesIndexList().contains(state)) {
                    complement.getFinalStatesIndexList().add(state);
                }
            }
            complement.setStartState(dfa.getStartState());
            for (Transition transition : dfa.getTransitionFunction()) {
                complement.getTransitionFunction().add(transition);
            }
            return complement;
    }



















    public static State getState(int n1, int n2, LinkedList<State> states){
            State myState = null;
            for (State state : states) {
                if (state.s1 == n1) {
                    if (state.s2 == n2) {
                        myState = state;
                    }
                }
            }
            return myState;
    }































    
    public static State getDestinationState(State originState, char alphabet, LinkedList<Transition> transitions){
            State destinationState = null;
            for (Transition transition : transitions) {
                if (transition.s1.equals(originState)) {
                    if (transition.alphabet == alphabet) {
                        destinationState = transition.s2;
                    }
                }
            }
            return destinationState;
    }

























    public static NewState getDestinationNewState(NewState originState, char alphabet, LinkedList<NewTransition> transitions){
            NewState destinationState = null;
            for (NewTransition transition : transitions) {
                if (transition.s1.equals(originState)) {
                    if (transition.alphabet == alphabet) {
                        destinationState = transition.s2;
                    }
                }
            }
            return destinationState;
    }




















    public static NewState getNewState(State state1, State state2, LinkedList<NewState> newStates){
            NewState newState = null;
            for (NewState state : newStates) {
                if (state.s1.equals(state1)) {
                    if (state.s2.equals(state2)) {
                        newState = state;
                    }
                }
            }
            return newState;
    }
}

























class DFA{
    private State startState;
    final private int numberOfStates;
    final private LinkedList<State> states= new LinkedList<>();
    final private int numberOfAlphabetLetters;
    final private LinkedList<State> finalStatesIndexList= new LinkedList<>();
    final private LinkedList<Transition> transitionFunction= new LinkedList<>();
    public DFA(int numberOfAlphabetLetters, int numberOfStates) {
        this.numberOfAlphabetLetters = numberOfAlphabetLetters;
        this.numberOfStates= numberOfStates;
    }
    public State getStartState() {
        return startState;
    }
    public void setStartState(State startState) {
        this.startState = startState;
    }
    public int getNumberOfStates() {
        return numberOfStates;
    }
    public LinkedList<State> getStates() {
        return states;
    }
    public int getNumberOfAlphabetLetters() {
        return numberOfAlphabetLetters;
    }
    public LinkedList<State> getFinalStatesIndexList() {
        return finalStatesIndexList;
    }
    public LinkedList<Transition> getTransitionFunction() {
        return transitionFunction;
    }
}

























class NewDFA{
    private NewState startState;
    final private int numberOfStates;
    final private LinkedList<NewState> states= new LinkedList<>();
    final private int numberOfAlphabetLetters;
    final private LinkedList<NewState> finalStatesIndexList= new LinkedList<>();
    final private LinkedList<NewTransition> transitionFunction= new LinkedList<>();
    public NewDFA(int numberOfStates, int numberOfAlphabetLetters) {
        this.numberOfStates = numberOfStates;
        this.numberOfAlphabetLetters = numberOfAlphabetLetters;
    }
    public NewState getStartState() {
        return startState;
    }
    public void setStartState(NewState startState) {
        this.startState = startState;
    }
    public int getNumberOfStates() {
        return numberOfStates;
    }
    public LinkedList<NewState> getStates() {
        return states;
    }
    public int getNumberOfAlphabetLetters() {
        return numberOfAlphabetLetters;
    }
    public LinkedList<NewState> getFinalStatesIndexList() {
        return finalStatesIndexList;
    }
    public LinkedList<NewTransition> getTransitionFunction() {
        return transitionFunction;
    }
}


















class State{
    final int s1;
    final int s2;
    public State(int s1, int s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public String toString() {
        return "{" + "s1=" + s1 + ", s2=" + s2 + '}';
    }
}






















class NewState{
    final State s1;
    final State s2;
    public NewState(State s1, State s2) {
        this.s1 = s1;
        this.s2 = s2;
    }
    @Override
    public String toString() {
        return "{" + "s1=" + s1 + ", s2=" + s2 + '}';
    }
}





























class Transition{
    final State s1;
    final char alphabet;
    final State s2;
    public Transition(State s1, char alphabet, State s2) {
        this.s1 = s1;
        this.alphabet= alphabet;
        this.s2 = s2;
    }
    @Override
    public String toString() {
        return "s1=" + s1 + ", alphabet=" + alphabet + ", s2=" + s2 ;
    }
}





























class NewTransition{
    final NewState s1;
    final char alphabet;
    final NewState s2;
    public NewTransition(NewState s1, char alphabet, NewState s2) {
        this.s1 = s1;
        this.alphabet = alphabet;
        this.s2 = s2;
    }
    @Override
    public String toString() {
        return "{s1=" + s1 + ", alphabet=" + alphabet + ", s2=" + s2+"}";
    }
}
























































class BFS {

    static LinkedList<LinkedList<Integer>> createGraph(int n){
        LinkedList<LinkedList<Integer>> graph= new LinkedList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new LinkedList<Integer>());
        }
        return graph;
    }

    static void addEdge(LinkedList<LinkedList<Integer>> graph ,int u ,int v){
        graph.get(u).add(v);
    }

    static String bfs(LinkedList<LinkedList<Integer>> graph, int v, int n){
        boolean[]visited=new boolean[graph.size()];
        LinkedList<Integer>queue=new LinkedList<>();
        LinkedList<Integer>r=new LinkedList<>();
        queue.add(v);
        visited[v]=true;
        //System.out.println(v);
        r.add(v);
        while (!queue.isEmpty()){
            int u=queue.get(0);
            queue.remove(0);
            for (int x:graph.get(u)) {
                if (!visited[x]){
                    queue.add(x);
                    visited[x]=true;
                    //System.out.println(x);
                    r.add(x);
                }
            }
        }
        String str="";
        if (r.contains(n)){
            str="YES";
        }
        else {
            str="NO";
        }
        return str;
    }
}
