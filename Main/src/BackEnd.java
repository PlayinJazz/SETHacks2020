import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/*
Author: Alex Liu, Michael Cheng
Date: July 18th, 2020
Description: The backend (variable change, synthesizing data, etc) portion of the project
 */

public class BackEnd {

    //variables for speed
    static int speed = 2;

    //variables for fish
    static int numFish = 0;
    static double food = 4.5;
    static ArrayList<Fish> fishes = new ArrayList<>();

    //variables for plants
    static int numPlants = 0;
    static int health = 0;
    static int plantInput = 0;
    static int plantOutput = 0;
    static int plantLifespan = 5;

    //variables for the tank
    static double pH = 7.5;
    static int volume = 1000;
    static double roF = 2.31;
    static float wtemp = 20;
    static float ptemp = 20;

    //Timer
    private static Timer myTimer;
    private static long totalElapsedTime = 0;
    private static long elapsedTime = 0;

    public void changeVolume(int n)
    {
        volume = n;
    }

    public static double getFood()
    {
        return food / fishes.size();
    }
    
    public static void addFish()
    {
        fishes.add(new Fish());
    }

    //Start the timer
    public static void startTime() {
        long startTime = System.currentTimeMillis();

        myTimer=new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                elapsedTime =(System.currentTimeMillis() - startTime) * speed + totalElapsedTime;
                long elapsedDays = elapsedTime / 1000;
                long elapsedYears = elapsedDays / 365;
                
                System.out.println(getDays());
                
                ArrayList<Integer> deadFishes = new ArrayList<>();
                
                if (getNumFishes() > 0)
                {
                    for (Fish fish : fishes)
                    {
                        fish.updateWeight();
                        if (checkDeadFish(fish))
                        {
                            deadFishes.add(fishes.indexOf(fish));
                        }
                    }
                    
                    for (int i = deadFishes.size()-1; i >= 0; i--)
                    {
                        fishes.remove(i);
                        System.out.println("Fish died");
                        myTimer.cancel();
                    }
                }
            }
        };

        myTimer.schedule(task,0,1000/speed);
    }
    
    public static int getDays()
    {
        return (int) ((elapsedTime + totalElapsedTime)/1000);
    }
    
    public static double getpH()
    {
        return pH;
    }
    
    public static boolean checkDeadFish(Fish f)
    {
        return (Math.random() * 100) <= f.getDeathChance();
    }

    public static double getTotalFishOutput()
    {
        int output = 0;
        
        for (Fish f : fishes)
        {
            output += f.getOutput();
        }
        
        return output;
    }
    
    public static int getNumFishes()
    {
        return fishes.size();
    }
    
    //Pause the timer
    public static void changeSpeed(int s)
    {
        totalElapsedTime = elapsedTime;
        speed = s;
        myTimer.cancel();

        if (s != 0)
            resume();
    }

    //Resume the timer
    public static void resume()
    {
        startTime();
    }

    public static void main(String[] args)
    {
        addFish();
        startTime();
    }
}
