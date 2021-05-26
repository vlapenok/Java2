public class Run implements Sport {
    private String name;
    private int length;

    public Run(String name, int length) {
        this.name = name;
        this.length = length;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    @Override
    public void training(String name) {
        System.out.println(name + " пробежит");
    }

    @Override
    public void training(Athlete athlete) {
        if(athlete.getMaxRun() >= length) {
            System.out.println(athlete.getName() + " пробежит");
        } else {
            System.out.println(athlete.getName() + " не сможет пробежать");
        }
    }
}