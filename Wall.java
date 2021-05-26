public class Wall implements Sport {
    private String name;
    private int height;

    public Wall(String name, int height) {
        this.name = name;
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void training(String name) {
        System.out.println(name + " перепрыгнет");
    }

    @Override
    public void training(Athlete athlete) {
        if(athlete.getMaxJump() >= height) {
            System.out.println(athlete.getName() + " перепрыгнет");
        } else {
            System.out.println(athlete.getName() + " не сможет перепрыгнуть");
        }
    }
}