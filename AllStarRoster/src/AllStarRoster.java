// AllStarRoster.java
// By: John Farina

class AllStarRoster {
    public static void main(String[] args) {
        abstract class ProPlayer {

            protected String name;
            private double baseSal = 1000000;


            ProPlayer(String name) {
                this.name = name;
            }

            public double getBaseSal() {
                return baseSal;
            }

            public void print() {
                System.out.print(name);
            }

            public abstract double pay();
        }
        class HockeyPlayer extends ProPlayer {

            private String teamName;
            private double bonus;

            HockeyPlayer(String name, String tname, double bonus) {
                super(name);
                teamName = tname;
                this.bonus = bonus;
            }

            public void print() {
                super.print();
                System.out.print(" plays for the " + teamName);
            }

            public double pay() {
                return super.getBaseSal() + bonus;
            }
        }

        HockeyPlayer players[] = new HockeyPlayer[6];
        players[0] = new HockeyPlayer("Gordie Howe", "Red Wings", 246532);
        players[1] = new HockeyPlayer("Alex Delvecchio", "Black Hawks", 350600);
        players[2] = new HockeyPlayer("Norm Ullman", "Maple Leafs", 1286700);
        players[3] = new HockeyPlayer("Stan Mikita", "Black Hawks", 2736120);
        players[4] = new HockeyPlayer("Wayne Gretzky", "Kings", 18946);
        players[5] = new HockeyPlayer("Denis Potvin", "Islanders", 353412);

        for (int i = 0; i < 6; i++) {
            players[i].print();
            System.out.println(" and makes $" + players[i].pay() + " a year.");
        }
    }
}
