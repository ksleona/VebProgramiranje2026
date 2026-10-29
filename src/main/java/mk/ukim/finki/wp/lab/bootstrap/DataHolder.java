package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Registration;
import mk.ukim.finki.wp.lab.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Workshop> workshops = null;
    public static List<Registration> registrations = null;

    @PostConstruct
    public void init() {
        workshops = new ArrayList<>();
        registrations = new ArrayList<>();
        workshops.add(new Workshop("Intro to Spring Boot", "Programming", 1500.0, 20));
        workshops.add(new Workshop("Advanced Java Streams", "Programming", 1800.0, 15));
        workshops.add(new Workshop("UI/UX Design Basics", "Design", 1200.0, 25));
        workshops.add(new Workshop("Machine Learning with Python", "Data Science", 2500.0, 10));
        workshops.add(new Workshop("Ethical Hacking 101", "Security", 2200.0, 0));
        workshops.add(new Workshop("Docker and Kubernetes", "DevOps", 2000.0, 12));
        workshops.add(new Workshop("Public Speaking", "Soft Skills", 800.0, 30));
        workshops.add(new Workshop("SQL for Beginners", "Databases", 1000.0, 18));
        workshops.add(new Workshop("Digital Photography", "Art", 900.0, 0));
        workshops.add(new Workshop("React Fundamentals", "Programming", 1700.0, 8));

    }
}
