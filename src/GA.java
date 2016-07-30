import java.util.ArrayList;
import java.util.Collections;

public class GA {
    //constants
    public static final int codeLength = 10;
    public static final int populationSize = 20;
    public static final int numOfGenerations = 300;
    public static final int numOfGARunning = 150;
    public static final double mutationProbability = 0.8;
    //colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // crossover
    public static ArrayList<int[]> crossover(Code a, Code b) {
        //between index 1 and lastIndex-1
        int index = (int) (Math.random() * (codeLength - 2)) + 1;
        //init the new arrays
        int[] first = new int[codeLength];
        int[] second = new int[codeLength];
        //copy the first part
        for (int i = 0; i < index; i++) {
            first[i] = a.getArray()[i];
            second[i] = b.getArray()[i];
        }
        //copy the second part
        for (int i = index; i < codeLength; i++) {
            first[i] = b.getArray()[i];
            second[i] = a.getArray()[i];
        }
        ArrayList<int[]> arr = new ArrayList();
        arr.add(first);
        arr.add(second);
        return arr;
    }

    // mutation
    public static void mutation(Code code) {
        if (Math.random() < mutationProbability) {
            int index = (int) (Math.random() * codeLength);
            code.getArray()[index] = (int) (Math.random() * codeLength);
        }
    }

    //fitness function
    public static int fitness(Code code) {
        int grade = 0;
        for (int i = 0; i < code.getArray().length; i++) {
            int appearance = code.getArray()[i];
            int sum = 0;
            // if appearance is 9 and num is 0 - then, num 0 needs to be 9 times.
            for (int j = 0; j < code.getArray().length; j++) {
                if (code.getArray()[j] == i)
                    sum++;
            }
            grade += Math.abs(appearance - sum);
        }
        return grade;
    }

    //GA
    public static boolean GA() {
        ArrayList<Code> population = new ArrayList();
        // create the first population
        for (int i = 0; i < populationSize; i++) {
            population.add(new Code(true));
        }
        // evaluate the first population
        for (Code code : population) {
            code.setGrade(fitness(code));
        }
        //sort the first population
        Collections.sort(population);
        //print first population
        //printList(population);
        //start generations
        for (int i = 0; i < numOfGenerations; i++) {
            ArrayList<Code> newPop = new ArrayList<Code>();
            //set the strongest parents
            for (int j = 0; j < populationSize / 2; j++) {
                newPop.add(population.get(j));
            }
            ArrayList<Code> children = new ArrayList<Code>();
            for (int j = 0; j < populationSize / 2; j++) {
                Code first = population.get(j);
                Code second = population.get(j + 1);
                ArrayList<int[]> cross = crossover(first, second);
                //create the new children
                Code a = new Code(false);
                Code b = new Code(false);
                //set the new data
                a.setArray(cross.get(0));
                b.setArray(cross.get(0));
                mutation(a);
                mutation(b);
                //set them to list of the children
                children.add(a);
                children.add(b);
            }
            // evaluate the population
            for (Code code : children) {
                int grade = fitness(code);
                code.setGrade(grade);
                if (grade == 0) {
                    System.out.println(ANSI_BLUE + "Result: " + code + ANSI_RESET);
                    System.out.println("Num of generations: " + i);
                    System.out.println();
                    return true;
                }
            }
            //get the strongest children
            for (int j = 0; j < populationSize / 2; j++) {
                newPop.add(children.get(j));
            }
            //set the old pop to the new one
            population = newPop;
            Collections.sort(population);
        }
        //print best
        System.out.println(ANSI_RED + "Best in this GA loop = " + population.get(0) + ANSI_RESET);
        return false;
    }

    /**
     * print list of codes
     *
     * @param list
     */
    public static void printList(ArrayList<Code> list) {
        for (Code code : list) {
            System.out.println(code);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int i = 0;
        for (; i < numOfGARunning; i++) {
            if (GA()) {
                System.out.println("The GA was run " + i + " times");
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Running time = " + totalTime + "ms");
        System.out.println("Num of tries ~= " + i * populationSize * numOfGenerations);
    }

}
