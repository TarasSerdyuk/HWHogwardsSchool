SELECT student.name, student.age, faculty.name FROM student
         LEFT JOIN faculty on faculty.id = student.faculty_id

SELECT student.name, student.age FROM student
         JOIN avatar ON student.id = avatar.student_id;