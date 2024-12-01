package utils;

public class RandomInt {

    public int getRandomInt(int min, int max) {
        return (int) (min + Math.random() * max + 1);
    }
}
