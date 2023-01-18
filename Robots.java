/* There are some robots and factories on the X-axis... You are given an integer array robot where robot[i] is the position of the ith robot... You are also given a 2D integer array factory where factory[j] = [positionj, limitj] indicates that positionj is the position of the jth factory and that the jth factory can repair at most limitj robots... The positions of each robot are unique... The positions of each factory are also unique... Note that a robot can be in the same position as a factory initially... All the robots are initially broken; they keep moving in one direction... The direction could be the negative or the positive direction of the X-axis... When a robot reaches a factory that did not reach its limit, the factory repairs the robot, and it stops moving... At any moment, you can set the initial direction of moving for some robot... Your target is to minimize the total distance traveled by all the robots... Return the minimum total distance traveled by all the robots. The test cases are generated such that all the robots can be repaired...
Note that
* All robots move at the same speed...
* If two robots move in the same direction, they will never collide...
* If two robots move in opposite directions and they meet at some point, they do not collide. They cross each other...
* If a robot passes by a factory that reached its limits, it crosses it as if it does not exist...
* If the robot moved from a position x to a position y, the distance it moved is |y - x|...
   * Eg 1: robot = [0,4,6]    factory = [[2,2],[6,2]]                     Output = 4 = (6-6)+(6-4)+(4-2)
   * Eg 2: robot = [1,-1]     factory = [[-2,1],[2,1]]                    Output = 2 = (2-1)+(2-1)
   * Eg 3: robot = [1,5,8]    factory = [[3,1],[9,2]]                     Output = 7 = (9-8)+(9-5)+(3-1)
 */
import java.util.*;
public class Robots
{
    public long MinimumDistance(List<Integer> robot, int[][] factory)
    {
        int position[] = new int[robot.size()];
        for(int i = 0; i < robot.size(); i++)
            position[i] = robot.get(i);
        Arrays.sort(position);      // Sorting Array in Ascending order...
        Arrays.sort(factory, (a,b) -> a[0] - b[0]);   // Sorting in Increasing Order...
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int dp1[] = new int[position.length];     // Creating a DP Array to store the minimum distance for each robot to reach the possible factory where it can be repaired...
        Arrays.fill(dp1, 10001);    // Filling with maximum value...
        long minimum = 0l;
        for(int i = 0; i < position.length; i++)
        {
            int index = 0;    // Index storing for the factory visited by the current robot...
            for(int j = 0; j < factory.length; j++)
            {
                map.putIfAbsent(factory[j][0], factory[j][1]);    // Putting the factory into the HashMap by factory position and capacity as its value...
                if(map.get(factory[j][0]) == 0)      // If current factory's capacity is hit...
                    continue;
                dp1[i] = Math.min(dp1[i], Math.abs(position[i] - factory[j][0]));    // Finding the minimum distance...
                if(dp1[i] == Math.abs(position[i] - factory[j][0]))
                    index = j;     // Storing the index of the factory to be reached...
            }
            System.out.println("Dp :"+i+" value : "+dp1[i]+" index : "+index);
            System.out.println("Map : "+map);
            if(map.get(factory[index][0]) != 0)    
                map.replace(factory[index][0], map.get(factory[index][0])-1);    // The capacity of the current factory decreases by one...
            minimum = minimum + dp1[i];     // Adding the minimum distance traversed...
        }
        return minimum;
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int x, k;
        System.out.print("Enter the number of Robots : ");
        x = sc.nextInt();
        List<Integer> location = new ArrayList<Integer>(x);
        for(int i = 0; i < x; i++)
        {
            System.out.print("Enter X Coordinate of "+(i+1)+" th robot : ");
            k = sc.nextInt();
            location.add(i, k);
        }
        System.out.print("Enter the number of factories : ");
        x = sc.nextInt();
        int factory[][] = new int[x][2];
        for(int i = 0; i < factory.length; i++)
        {
            System.out.print("Enter X Coordinate of "+(i+1)+" th Factory : ");
            x = sc.nextInt();
            factory[i][0] = x;
            System.out.print("Enter the Capacity of "+(i+1)+" th Factory : ");
            x = sc.nextInt();
            factory[i][1] = x;
        }
        Robots robots = new Robots();     // Object creation...
        System.out.println("The Minimum Distance Traversed : "+robots.MinimumDistance(location, factory));
        sc.close();
    }
}

// Time Complexity  - O(n^2) time...
// Space Complexity - O(n) space...

/* DEDUCTIONS :- 
 * 1. We can sort the arrays of position of both robots and factory...
 * 2. Since each factory has certain capacity, the capacity is a limiting factor which determines which robot must enter a certain factory...
 * 3. The capacity of a factory can be updated quickly using a Hashtable and the minimum distance of every factory from every robot can be determined and added up keeping in mind about the capacity of each factory...
*/