import datetime
import random
import psycopg2
import os
from dotenv import load_dotenv
from pathlib import Path

env_path = Path('../..') / '.env'
load_dotenv(dotenv_path=env_path)

NUMBER_OF_LESSONS = 100

courses = ["Software Project", "Introduction to AI", "Networks",
           "Data Modeling and Databases II", "Probability and Statistics"]

users = []
professors = [["n.shilov@innopolis.ru", None, "Shilov", "Nikolai", None]]
admins = [["salo@innopolis.ru", None, "Salo", "Maxim", None]]
does = [["a.dolgoborodov@innopolis.ru", None, "Dolgoborodov", "Aleksandr", None]]
tas = [["n.bogomazov@innopolis.ru", None, "Bogomazov", "Nikita", None]]

with open("users.txt", "r") as f:
    for line in f:
        users.append(line[:-4].strip().split("\t"))

for i in range(len(users)):
    password = "$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe"
    users[i].append(password)

    role = "ROLE_STUDENT"
    users[i].append(role)

    birthday = datetime.date(random.randint(1999, 2000), random.randint(1, 12), random.randint(1, 27))
    users[i].append(birthday)

for i in range(len(professors)):
    password = "$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe"
    professors[i].append(password)

    role = "ROLE_PROFESSOR"
    professors[i].append(role)

    birthday = datetime.date(random.randint(1960, 1970), random.randint(1, 12), random.randint(1, 27))
    professors[i].append(birthday)

for i in range(len(admins)):
    password = "$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe"
    admins[i].append(password)

    role = "ROLE_ADMIN"
    admins[i].append(role)

    birthday = datetime.date(random.randint(2009, 2011), random.randint(1, 12), random.randint(1, 27))
    admins[i].append(birthday)

for i in range(len(does)):
    password = "$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe"
    does[i].append(password)

    role = "ROLE_DOE"
    does[i].append(role)

    birthday = datetime.date(random.randint(1970, 1980), random.randint(1, 12), random.randint(1, 27))
    does[i].append(birthday)

for i in range(len(tas)):
    password = "$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe"
    tas[i].append(password)

    role = "ROLE_TA"
    tas[i].append(role)

    birthday = datetime.date(random.randint(1980, 1990), random.randint(1, 12), random.randint(1, 27))
    tas[i].append(birthday)

users.extend(professors)
users.extend(admins)
users.extend(does)
users.extend(tas)

conn_params = {
    'dbname': os.getenv("POSTGRES_DB"),
    'user': os.getenv("POSTGRES_USER"),
    'password': os.getenv("POSTGRES_PASSWORD"),
    'host': "localhost"
}
conn = psycopg2.connect(**conn_params)

with conn.cursor() as cursor:
    for i in range(len(users)):
        user_id = i + 1
        birthday = users[i][7]
        email = users[i][0]
        surname = users[i][2]
        password = users[i][5]
        role = users[i][6]
        name = users[i][3]

        cursor.execute(
            "insert into user_profile (id, birthday, email, surname, password, role, name, group_number) "
            "values (%s, %s, %s, %s, %s, %s, %s, %s)",
            [user_id, birthday, email, surname, password, role, name, 0])
    conn.commit()

    for i in range(len(courses)):
        course_id = i + 1
        course_name = courses[i]

        cursor.execute("insert into course (id, name) values (%s, %s)", [course_id, course_name])
    conn.commit()

    for i in range(len(courses)):
        course_id = i + 1

        for j in range(len(users)):
            is_professor = False
            is_ta = False
            user_role = users[j][6]
            participant_id = j + 1

            if user_role == "ROLE_ADMIN" or user_role == "ROLE_DOE":
                continue
            elif user_role == "ROLE_PROFESSOR" and not is_professor:
                is_professor = True
                cursor.execute("insert into course_participants (course_id, participant_id) values (%s, %s)",
                               [course_id, participant_id])
            elif user_role == "ROLE_TA" and not is_ta:
                is_ta = True
                cursor.execute("insert into course_participants (course_id, participant_id) values (%s, %s)",
                               [course_id, participant_id])
            elif random.uniform(0.0, 9.0) < 7:
                cursor.execute("insert into course_participants (course_id, participant_id) values (%s, %s)",
                               [course_id, participant_id])
    conn.commit()

    for i in range(len(courses)):
        course_id = i + 1

        cursor.execute("select user_profile.id from course_participants, user_profile where "
                       "user_profile.id = course_participants.participant_id and "
                       "course_participants.course_id = %s and user_profile.role = 'ROLE_PROFESSOR'", [course_id])
        professors = cursor.fetchall()

        cursor.execute("select user_profile.id from course_participants, user_profile where "
                       "user_profile.id = course_participants.participant_id and "
                       "course_participants.course_id = %s and user_profile.role = 'ROLE_TA'", [course_id])
        ta = cursor.fetchall()

        cursor.execute("select user_profile.id from course_participants, user_profile where "
                       "user_profile.id = course_participants.participant_id and "
                       "course_participants.course_id = %s and user_profile.role = 'ROLE_STUDENT'", [course_id])
        students = cursor.fetchall()

        for j in range(NUMBER_OF_LESSONS):
            rlesson_type = random.randint(0, 2)
            lesson_type = ""
            teacher = 0

            if rlesson_type == 0:
                lesson_type = "LECTURE"
                teacher = professors[random.randint(0, len(professors) - 1)][0]
            elif rlesson_type == 1:
                lesson_type = "TUTORIAL"
                teacher = professors[random.randint(0, len(professors) - 1)][0]
            elif rlesson_type == 2:
                lesson_type = "LAB"
                teacher = ta[random.randint(0, len(ta) - 1)][0]

            lesson_id = course_id * 1000 + (j + 1)

            lesson_date_time = datetime.datetime(2019, random.randint(1, 12), random.randint(1, 27), hour=9, minute=30)

            lesson_room = random.randint(1, 3)
            if lesson_room == 2:
                lesson_room = 3
            if lesson_room == 1:
                lesson_room = lesson_room * 100 + random.randint(5, 8)
            else:
                lesson_room = lesson_room * 100 + random.randint(1, 21)

            cursor.execute(
                "insert into lesson (id, date_time, room, type, course_id, teacher_id) values "
                "(%s, %s, %s, %s, %s, %s)",
                [lesson_id, lesson_date_time, lesson_room, lesson_type, course_id, teacher])

            number_of_attended = random.randint(1, len(students) - 1)
            _cstudents = random.randint(0, len(students) - number_of_attended - 1)

            for student in students[_cstudents:(_cstudents + number_of_attended)]:
                cursor.execute("insert into lesson_student (student_id, lesson_id, check_in_time, check_out_time)"
                               "values (%s, %s, %s, %s)",
                               [student[0], lesson_id,
                                lesson_date_time, lesson_date_time.replace(hour=11, minute=0)])
    conn.commit()
