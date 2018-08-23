package hacker;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;


/*To continue : https://www.hackerrank.com/challenges/climbing-the-leaderboard/problem?h_r=internal-search*/

public class Solution {

    private static final Scanner scanner = new Scanner(System.in);

    // Complete the climbingLeaderboard function below.
    static int[] climbingLeaderboard(int[] scores, int[] alice) {

        /*Rank all scores
         * Loop until alice.hasMoreElements
         *   Find rank of one of alice's score
         *   sout rank
         * */
        int rank[] = new int[scores.length];
        for (int i = 0; i < scores.length; i++) {
            if (i == 0) {
                rank[i] = 1;
            } else {
                if (scores[i] == scores[i - 1]) {
                    rank[i] = rank[i - 1];
                } else {
                    rank[i] = rank[i - 1] + 1;
                }
            }
        }

        /*DEBUG-->See ranked scores*/
/*        for (int j=0;j<scores.length;j++){
            System.out.println("DEBUB: Show rank : "+rank[j]);
        }*/

        /*Perform binary search on sorted scores array for every element in alice array
         *  IF element already available in scores it will have the same rank
         *  IF element is not in array --> Reorder ranking
         * */
        int[] result = new int[alice.length];
        for (int rs = 0; rs < alice.length; rs++) {
            int indexIfFound = descendingSortedBinarySearch(scores, 0, scores.length - 1, alice[rs]);
            if (indexIfFound != -1) {
                //System.out.println("Rank of first alice score is :"+rank[indexIfFound]);
                result[rs] = rank[indexIfFound];
            } else {
                if (alice[rs] > scores[0]) {
                    //System.out.println("GR Score "+alice[rs]);
                    result[rs] = 1;

                } else if (alice[rs] < scores[scores.length - 1]) {
                    //System.out.println("LR Score "+alice[rs]);
                    result[rs] = rank[scores.length - 1] + 1;
                } else {

                    for (int loop = scores.length - 1; loop > 0; loop--) {
                        //System.out.println("IN Score "+alice[rs]);
                        if (alice[rs] > scores[loop] && alice[rs] < scores[loop - 1]) {
                            result[rs] = rank[loop];
                        }
                    }

                }
                //System.out.println("Rank of first alice score is to be calculated");
            }

        }
        return result;
    }

    /*Finds index of element in a sorted descending order array rather than ascending sorted if present
     * else return -1*/
    private static int descendingSortedBinarySearch(int descendingSortedArray[], int startIndex, int stopIndex, int elementToFind) {

        int mid;
        if (stopIndex >= startIndex) {
            mid = startIndex + (stopIndex - startIndex) / 2;

            /*if element is present in middle itself*/
            if (descendingSortedArray[mid] == elementToFind) {
                /*System.out.println("***Mid : "+mid);    //Uncomment to DEBUG*/
                return mid;
            }

            /*If element is smaller than mid, then it can only be present in right sub-array*/
            if (descendingSortedArray[mid] > elementToFind) {
                /*                System.out.println("***Return right ");    //Uncomment to DEBUG*/
                return descendingSortedBinarySearch(descendingSortedArray, mid + 1, stopIndex, elementToFind);
            }

            /*Else element can only be present in left sub-array*/
            /*System.out.println("***Return left ");    //Uncomment to DEBUG*/
            return descendingSortedBinarySearch(descendingSortedArray, startIndex, mid - 1, elementToFind);
        }

        /*We reach here only if element was not found*/
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int scoresCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] scores = new int[scoresCount];

        String[] scoresItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < scoresCount; i++) {
            int scoresItem = Integer.parseInt(scoresItems[i]);
            scores[i] = scoresItem;
        }

        int aliceCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] alice = new int[aliceCount];

        String[] aliceItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < aliceCount; i++) {
            int aliceItem = Integer.parseInt(aliceItems[i]);
            alice[i] = aliceItem;
        }

        int[] result = climbingLeaderboard(scores, alice);

        for (int i = 0; i < result.length; i++) {
            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
