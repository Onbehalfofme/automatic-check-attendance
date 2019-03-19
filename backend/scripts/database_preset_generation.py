import datetime
import random

import psycopg2

f = open("users.txt", "r")
users = []
values = []

conn = psycopg2.connect(dbname='attendance', user='postgres',
                        password='password', host='localhost')
cursor = conn.cursor()

for line in f:
    users.append(line[:-4].strip().split("\t"))
f.close()

for i in range(len(users)):
    users[i].append("$2a$11$Ah09LwhBrshoFwNyMJhyxu8eBGt2kdGuWWiVafLb6CqWVGlPGZtoe")

    role = ""
    irole = random.randint(0, 4)
    if irole == 0:
        role = "ROLE_STUDENT"
    elif irole == 1:
        role = "ROLE_TA"
    elif irole == 2:
        role = "ROLE_PROFESSOR"
    elif irole == 3:
        role = "ROLE_DOE"
    elif irole == 4:
        role = "ROLE_ADMIN"

    users[i].append(role)
    users[i].append(datetime.datetime.strptime("01/01/99 16:30", "%d/%m/%y %H:%M").date())

for i in range(len(users)):
    values.append((users[i][7], users[i][0], users[i][2], users[i][5], users[i][6], users[i][3]))

for i in range(len(values)):
    cursor.execute(
        'insert into user_profile (id, birthday, email, surname, password, role, name) '
        'values (%s, %s, %s, %s, %s, %s, %s)',
        [i + 1, values[i][0], values[i][1], values[i][2], values[i][3], values[i][4], values[i][5]])

conn.commit()

courses = ["Software Project", "Introduction to AI", "Networks",
           "Data Modeling and Databases II", "Probability and Statistics"]

for x in range(len(courses)):
    cursor.execute("insert into course (id, name) values (%s, %s)", [x + 1, courses[x]])

for i in range(5):
    for j in range(len(users)):
        if users[j][6] == "ROLE_ADMIN" or users[j][6] == "ROLE_DOE":
            continue
        if random.uniform(0.0, 9.0) < 7:
            cursor.execute('insert into course_participants (course_id, participant_id) values (%s, %s)',
                           [i + 1, j + 1])

conn.commit()

for i in range(5):
    cursor.execute("select user_profile.id from course_participants, user_profile where "
                   "user_profile.id = course_participants.participant_id and "
                   "course_participants.course_id = %s and user_profile.role = 'ROLE_PROFESSOR'", [i + 1])
    profs = cursor.fetchall()

    cursor.execute("select user_profile.id from course_participants, user_profile where "
                   "user_profile.id = course_participants.participant_id and "
                   "course_participants.course_id = %s and user_profile.role = 'ROLE_TA'", [i + 1])
    ta = cursor.fetchall()

    cursor.execute("select user_profile.id from course_participants, user_profile where "
                   "user_profile.id = course_participants.participant_id and "
                   "course_participants.course_id = %s and user_profile.role = 'ROLE_STUDENT'", [i + 1])
    students = cursor.fetchall()

    for j in range(100):
        itype = random.randint(0, 2)
        lesson_type = ""
        teacher = 0
        if itype == 0:
            lesson_type = "LECTURE"
            teacher = profs[random.randint(0, len(profs) - 1)][0]
        elif itype == 1:
            lesson_type = "TUTORIAL"
            teacher = profs[random.randint(0, len(profs) - 1)][0]
        elif itype == 2:
            lesson_type = "LAB"
            teacher = ta[random.randint(0, len(ta) - 1)][0]
        date_time = datetime.datetime.strptime("{0}/{1}/19 9:30".format(
            str(random.randint(1, 25)), str(random.randint(1, 12))), "%d/%m/%y %H:%M")

        cursor.execute(
            "insert into lesson (id, date_time, room, type, course_id, teacher_id) values "
            "(%s, %s, %s, %s, %s, %s)",
            [(i + 1) * 1000 + (j + 1), date_time,
             random.randint(100, 400), lesson_type,
             i + 1,
             teacher])

        sattended = random.randint(1, len(students) - 1)
        nstudents = random.randint(0, len(students) - 1 - sattended)

        for student in students[nstudents:nstudents + sattended]:
            cursor.execute("insert into lesson_student (student_id, lesson_id, check_in_time, check_out_time)"
                           "values (%s, %s, %s, %s)",
                           [student[0], (i + 1) * 1000 +
                            (j + 1), date_time, date_time.replace(hour=11, minute=0)])

conn.commit()
cursor.close()
