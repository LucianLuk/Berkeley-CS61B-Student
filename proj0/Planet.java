public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet planet) {
        this.xxPos = planet.xxPos;
        this.yyPos = planet.yyPos;
        this.xxVel = planet.xxVel;
        this.yyVel = planet.yyVel;
        this.mass = planet.mass;
        this.imgFileName = planet.imgFileName;
    }

    public double calcDistance(Planet planet) {
        double dx = this.xxPos - planet.xxPos;
        double dy = this.yyPos - planet.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet planet) {
        double G = 6.67e-11;
        double r = this.calcDistance(planet);
        return G * this.mass * planet.mass / (r * r);
    }

    public double calcForceExertedByX(Planet planet) {
        double dx = planet.xxPos - this.xxPos;
        double r = this.calcDistance(planet);
        double F = this.calcForceExertedBy(planet);
        return F * dx / r;
    }

    public double calcForceExertedByY(Planet planet) {
        double dy = planet.yyPos - this.yyPos;
        double r = this.calcDistance(planet);
        double F = this.calcForceExertedBy(planet);
        return F * dy / r;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        double netForceX = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            }
            netForceX += this.calcForceExertedByX(planet);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        double netForceY = 0;
        for (Planet planet : planets) {
            if (this.equals(planet)) {
                continue;
            }
            netForceY += this.calcForceExertedByY(planet);
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double aX = fX / this.mass;
        double aY = fY / this.mass;
        this.xxVel += dt * aX;
        this.yyVel += dt * aY;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
    }

    public void draw() {
        int waitTimeMilliseconds = 100;

        /* Stamp 100 additional pictures in random locations,
         * each one coming slightly faster than the one before. */
        int count = 0;
        while (count < 200) {
            /* picks random x and y between -90 and 90 */
            String imageToDraw = "images/" + this.imgFileName;

            double x = this.xxPos;
            double y = this.yyPos;

            /* Clears the screen. */
            StdDraw.picture(x, y, imageToDraw);
            StdDraw.show();
            StdDraw.pause(waitTimeMilliseconds);

            /* Reduce wait time for each thing drawn, but
             * never wait less than 10 milliseconds. */
            waitTimeMilliseconds = waitTimeMilliseconds - 1;
            if (waitTimeMilliseconds < 1) {
                waitTimeMilliseconds = 10;
            }

            count += 1;
        }
    }
}