
public class NBody {

    private static int num;

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        // Enable double buffering by calling enableDoubleBuffering().
        StdDraw.enableDoubleBuffering();

        double t = 0;
        while (t < T) {
            // Create an xForces array and yForces array.
            double[] xForces = new double[num];
            double[] yForces = new double[num];

            // Calculate the net x and y forces for each planet, storing these in the xForces and yForces arrays respectively.
            int i = 0;
            for (Planet planet : planets) {
                xForces[i] = planet.calcNetForceExertedByX(planets);
                yForces[i++] = planet.calcNetForceExertedByY(planets);
            }

            // Call update on each of the planets. This will update each planet’s position, velocity, and acceleration.
            i = 0;
            for (Planet planet : planets) {
                planet.update(dt, xForces[i], yForces[i++]);
            }

            // Draw the background image.
            drawThree(radius);

            // Draw all of the planets.
            for (Planet planet : planets) {
                planet.draw();
                drawZoom("images/" + planet.imgFileName);
            }

            // Show the offscreen buffer (see the show method of StdDraw).
            StdDraw.show();

            // Pause the animation for 10 milliseconds (see the pause method of StdDraw). You may need to tweak this on your computer.
            StdDraw.pause(10);

            // Increase your time variable by dt.
            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }

    public static double readRadius(String filename) {
        In in = new In(filename);
        num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String name = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, name);
        }
        return planets;
    }

    private static void drawThree(double Radius) {
        /** Sets up the universe so it goes from
         * -100, -100 up to 100, 100 */
        String imageToDraw = "images/starfield.jpg";

        double scale = Radius;
        StdDraw.setScale(-1 * Radius, Radius);

        /* Stamps three copies of advice.png in a triangular pattern. */
        StdDraw.picture(0, 75, imageToDraw);
        StdDraw.picture(-75, -75, imageToDraw);
        StdDraw.picture(75, -75, imageToDraw);

        /* Shows the drawing to the screen, and waits 2000 milliseconds. */
        StdDraw.show();
        StdDraw.pause(2000);
    }

    private static void drawZoom(String imageToDraw) {
        /** Enables double buffering.
         * An animation technique where all drawing takes place on the offscreen canvas.
         * Only when you call show() does your drawing get copied from the
         * offscreen canvas to the onscreen canvas, where it is displayed
         * in the standard drawing window. You don't have to understand this
         * for CS61B. Just know that if you don't call this function, any attempt
         * at smooth animation will look bad and flickery (remove it and see
         * what happens!). */
        StdDraw.enableDoubleBuffering();


        double size = 100;
        while (size < 500) {
            StdDraw.picture(0, 0, imageToDraw, size, size);
            StdDraw.show();
            StdDraw.pause(10);
            size += 1;
        }

        while (size > 1) {
            StdDraw.picture(0, 0, imageToDraw, size, size);
            StdDraw.show();
            StdDraw.pause(1);
            size -= 1;
        }
    }
}