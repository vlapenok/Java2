public class Cat implements Athlete {
    private String name;
    private int maxRun;
    private int maxJump;

    public Cat(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxRun() {
        return maxRun;
    }

    @Override
    public int getMaxJump() {
        return maxJump;
    }

    @Override
    public void info() {
        System.out.println(name + " бегает на " + maxRun + " и прыгает на " + maxJump);
    }

    @Override
    public void run(Run road) {
        if(maxRun >= road.getLength()) {
            road.training(name);
        } else {
            System.out.println(name + " не может столько пробежать");
        }
    }

    @Override
    public boolean run1(Object object) {
        if(maxRun >= ((Run) object).getLength()) {
            ((Run) object).training(name);
            return true;
        } else {
            System.out.println(name + " не может столько пробежать и выбывает из соревнований");
            return false;
        }
    }

    @Override
    public void jump(Wall jump) {
        if(maxJump >= jump.getHeight()) {
            jump.training(name);
        } else {
            System.out.println(name + " не может перепрыгнуть");
        }
    }

    @Override
    public boolean jump1(Object object) {
        if(maxJump >= ((Wall) object).getHeight()) {
            ((Wall) object).training(name);
            return true;
        } else {
            System.out.println(name + " не может столько пробежать и выбывает из соревнований");
            return false;
        }
    }

//    public void start(Sport newSport) {
//        if(sport == null) {
//            newSport.training(name);
//            sport = newSport;
//        } else if(!(sport.getName().equals(newSport.getName()))) {
//            System.out.println(name + " должен сначала закончить " + "\"" + sport.getName() + "\"");
//        } else {
//            System.out.println(name + " уже занимается " + "\"" + sport.getName() + "\"");
//        }
//    }
//
//    public void stop() {
//        if(sport != null) {
//            System.out.println(name + " закончил " + "\"" + sport.getName() + "\"");
//            sport = null;
//        } else {
//            System.out.println(name + " в данный момент ни чем не занимается");
//        }
//    }
}