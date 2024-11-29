package lk.ijse.culinaryacademy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollId;
    private double firstInstallment;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Programs programs; // Keep this as it is

    public Enrollment(double firstInstallment, double balance, Student student, Programs programs) {
        this.firstInstallment = firstInstallment;
        this.balance = balance;
        this.student = student;
        this.programs = programs;
    }

}
