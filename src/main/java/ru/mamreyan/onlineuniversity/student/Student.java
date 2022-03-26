package ru.mamreyan.onlineuniversity.student;

import com.sun.istack.NotNull;
import ru.mamreyan.onlineuniversity.group.Group;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Student {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String            lastName;
    @NotNull private String            firstName;
    private          String            middleName;
    private          Sex               sex;
    @NotNull private GregorianCalendar birthDate;
    @NotNull private GregorianCalendar entryDate;

    @NotNull
    @ManyToOne
    @JoinColumn (name = "group_id",
                 foreignKey = @ForeignKey (name = "group_id_fkey"))
    private Group group;

    @NotNull private boolean active;

    Student() {
    }

    Student(
            StudentBuilder studentBuilder
    ) {
        if (studentBuilder.lastName == null || studentBuilder.lastName.isBlank()) {
            throw new IllegalArgumentException("last name is null");
        }

        if (studentBuilder.firstName == null || studentBuilder.firstName.isBlank()) {
            throw new IllegalArgumentException("first name is null");
        }

        if (studentBuilder.sex == null) {
            throw new IllegalArgumentException("sex is null");
        }

        if (studentBuilder.birthDate == null) {
            throw new IllegalArgumentException("birth date is null");
        }

        if (studentBuilder.entryDate == null) {
            throw new IllegalArgumentException("entry date is null");
        }

        this.lastName   = studentBuilder.lastName;
        this.firstName  = studentBuilder.firstName;
        this.middleName = studentBuilder.middleName;
        this.sex        = studentBuilder.sex;
        this.birthDate  = studentBuilder.birthDate;
        this.entryDate  = studentBuilder.entryDate;
        this.group      = studentBuilder.group;
        this.active     = studentBuilder.active;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("last name is null");
        }

        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("first name is null");
        }

        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        if (sex == null) {
            throw new IllegalArgumentException("sex is null");
        }

        this.sex = sex;
    }

    public GregorianCalendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(GregorianCalendar birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("birth date is null");
        }

        this.birthDate = birthDate;
    }

    public GregorianCalendar getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(GregorianCalendar entryDate) {
        if (entryDate == null) {
            throw new IllegalArgumentException("entry date is null");
        }

        this.entryDate = entryDate;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFullName() {
        return new StringJoiner(" ").add(lastName).add(firstName).add(middleName).toString();
    }

    public boolean isNotValid() {
        return lastName == null ||
               lastName.isBlank() ||
               firstName == null ||
               firstName.isBlank() ||
               sex == null ||
               birthDate == null ||
               entryDate == null ||
               group == null;
        // entryDate == null;
    }

    public boolean isSamePerson(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Student student)) {
            return false;
        }

        return Objects.equals(
                this.lastName,
                student.lastName
        ) && Objects.equals(
                this.firstName,
                student.firstName
        ) && Objects.equals(
                this.middleName,
                student.middleName
        ) && Objects.equals(
                this.sex,
                student.sex
        ) && Objects.equals(
                this.birthDate,
                student.birthDate
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Student student)) {
            return false;
        }

        return Objects.equals(
                this.lastName,
                student.lastName
        ) && Objects.equals(
                this.firstName,
                student.firstName
        ) && Objects.equals(
                this.middleName,
                student.middleName
        ) && Objects.equals(
                this.sex,
                student.sex
        ) && Objects.equals(
                this.birthDate,
                student.birthDate
        ) && Objects.equals(
                this.entryDate,
                student.entryDate
        ) && Objects.equals(
                this.group,
                student.group
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.lastName,
                this.firstName,
                this.middleName,
                this.sex,
                this.birthDate,
                this.entryDate,
                this.group,
                this.active
        );
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        return (stringBuilder
                .append("Student №")
                .append(this.id)
                .append(": {\nlastName = ")
                .append(this.lastName)
                .append(",\nfirstName = ")
                .append(this.firstName)
                .append(",\nmiddleName = ")
                .append(this.middleName)
                .append(",\nsex = ")
                .append(this.sex)
                .append(",\nbirthDate = ")
                .append(this.birthDate == null ? "null" : this.birthDate.getTime())
                .append(",\nentryDate = ")
                .append(this.entryDate == null ? "null" : this.entryDate.getTime())
                .append(",\ngroup = ")
                .append(this.group)
                .append(",\nactive = ")
                .append(this.active)
                .append("\n}")).toString();
    }

    public static class StudentBuilder {
        private String            lastName;
        private String            firstName;
        private String            middleName;
        private Sex               sex;
        private GregorianCalendar birthDate;
        private GregorianCalendar entryDate;
        private Group             group;
        private boolean           active = true;

        public StudentBuilder() {
            super();
        }

        public StudentBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentBuilder middleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public StudentBuilder sex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public StudentBuilder birthDate(GregorianCalendar birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public StudentBuilder entryDate(GregorianCalendar entryDate) {
            this.entryDate = entryDate;
            return this;
        }

        public StudentBuilder group(Group group) {
            this.group = group;
            return this;
        }

        public StudentBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public Student build() {
            Student student = new Student(this);

            if (student.isNotValid()) {
                throw new StudentNotValidException(student);
            }

            return student;
        }
    }
}