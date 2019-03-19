/*var request = new XMLHttpRequest();
request.open('GET', '/users/create?course=someCourse&type=someType&date=someDate&time=someTime', true);
request.send();*/

table = [
  {
    "id": 2,
    "email": "a.gromova@innopolis.ru",
    "name": "Anastasiia",
    "surname": "Gromova",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 4,
    "email": "m.salo@innopolis.ru",
    "name": "Maxim",
    "surname": "Salo",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 5,
    "email": "a.andryukov@innopolis.ru",
    "name": "Alexander",
    "surname": "Andryukov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 9,
    "email": "v.vertash@innopolis.ru",
    "name": "Valeriya",
    "surname": "Vertash",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 10,
    "email": "a.shabanov@innopolis.ru",
    "name": "Alexey",
    "surname": "Shabanov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 13,
    "email": "n.poryvaev@innopolis.ru",
    "name": "Nikita",
    "surname": "Poryvaev",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 14,
    "email": "e.romanov@innopolis.ru",
    "name": "Evgeny",
    "surname": "Romanov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 17,
    "email": "e.lukyanchikova@innopolis.ru",
    "name": "Elena",
    "surname": "Lukyanchikova",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 18,
    "email": "a.minakova@innopolis.ru",
    "name": "Anastasiia",
    "surname": "Minakova",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
  },
  {
    "id": 21,
    "email": "k.alimov@innopolis.ru",
    "name": "Kamil",
    "surname": "Alimov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 24,
    "email": "i.mazan@innopolis.ru",
    "name": "Ilia",
    "surname": "Mazan",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 25,
    "email": "i.potemin@innopolis.ru",
    "name": "Ilya",
    "surname": "Potemin",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 29,
    "email": "y.suhorukov@innopolis.ru",
    "name": "Yuriy",
    "surname": "Sukhorukov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 32,
    "email": "a.kuspakov@innopolis.ru",
    "name": "Amadey",
    "surname": "Kuspakov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 34,
    "email": "a.subaev@innopolis.ru",
    "name": "Amir",
    "surname": "Subaev",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
  },
  {
    "id": 36,
    "email": "a.ahmetbek@innopolis.ru",
    "name": "Ali",
    "surname": "Akhmetbek",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 37,
    "email": "j.badour@innopolis.ru",
    "name": "Jafar",
    "surname": "Badour",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 38,
    "email": "e.bondarev@innopolis.ru",
    "name": "Eugene",
    "surname": "Bondarev",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 39,
    "email": "d.kochetov@innopolis.ru",
    "name": "Dmitrii",
    "surname": "Kochetov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
  },
  {
    "id": 40,
    "email": "e.baklanov@innopolis.ru",
    "name": "Egor",
    "surname": "Baklanov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 41,
    "email": "i.prokopev@innopolis.ru",
    "name": "Ilia",
    "surname": "Prokopev",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 42,
    "email": "a.sedova@innopolis.ru",
    "name": "Aleksandra",
    "surname": "Sedova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
  },
  {
    "id": 44,
    "email": "a.zagidullina@innopolis.ru",
    "name": "Aliya",
    "surname": "Zagidullina",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
  },
  {
    "id": 45,
    "email": "f.galeev@innopolis.ru",
    "name": "Farit",
    "surname": "Galeev",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
  },
  {
    "id": 47,
    "email": "d.miklashevskaya@innopolis.ru",
    "name": "Daria",
    "surname": "Miklashevskaya",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 48,
    "email": "t.mustafin@innopolis.ru",
    "name": "Timur",
    "surname": "Mustafin",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 51,
    "email": "f.hakimov@innopolis.ru",
    "name": "Farhad",
    "surname": "Khakimov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 53,
    "email": "k.ahmetov@innopolis.ru",
    "name": "Kamil",
    "surname": "Akhmetov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 56,
    "email": "n.mikryukov@innopolis.ru",
    "name": "Nikolai",
    "surname": "Mikriukov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 57,
    "email": "n.novarlic@innopolis.ru",
    "name": "Nikola",
    "surname": "Novarlić",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 59,
    "email": "e.patrusheva@innopolis.ru",
    "name": "Patrusheva",
    "surname": "Elena",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 61,
    "email": "d.kalinin@innopolis.ru",
    "name": "Danil",
    "surname": "Kalinin",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 62,
    "email": "i.komarov@innopolis.ru",
    "name": "Ivan",
    "surname": "Komarov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 65,
    "email": "d.begishev@innopolis.ru",
    "name": "Danis",
    "surname": "Begishev",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 69,
    "email": "a.yurin@innopolis.ru",
    "name": "Artem",
    "surname": "IUrin",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 70,
    "email": "e.kolchanova@innopolis.ru",
    "name": "Elizaveta",
    "surname": "Kolchanova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 71,
    "email": "d.levkovets@innopolis.ru",
    "name": "Denis",
    "surname": "Levkovets",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 72,
    "email": "l.lygin@innopolis.ru",
    "name": "Leonid",
    "surname": "Lygin",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 76,
    "email": "i.nazmiev@innopolis.ru",
    "name": "Irek",
    "surname": "Nazmiev",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 77,
    "email": "a.rahimov@innopolis.ru",
    "name": "Abdurasul",
    "surname": "Rahimov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 78,
    "email": "ay.ahmetshin@innopolis.ru",
    "name": "Aidar",
    "surname": "Ahmetshin",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 79,
    "email": "i.fathullin@innopolis.ru",
    "name": "Ilshat",
    "surname": "Fatkhullin",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 80,
    "email": "r.rahimov@innopolis.ru",
    "name": "Rim",
    "surname": "Rakhimov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 81,
    "email": "s.semenyuk@innopolis.ru",
    "name": "Svyatoslav",
    "surname": "Semenyuk",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 83,
    "email": "v.smirnov@innopolis.ru",
    "name": "Vladislav",
    "surname": "Smirnov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 88,
    "email": "m.bobrov@innopolis.ru",
    "name": "Mikhail",
    "surname": "Bobrov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 92,
    "email": "l.gumerov@innopolis.ru",
    "name": "Lenar",
    "surname": "Gumerov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 97,
    "email": "k.kryachkina@innopolis.ru",
    "name": "Kamilla",
    "surname": "Kryachkina",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 99,
    "email": "s.kabirova@innopolis.ru",
    "name": "Svetlana",
    "surname": "Kabirova",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 102,
    "email": "k.saitov@innopolis.ru",
    "name": "Kamil",
    "surname": "Saitov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 103,
    "email": "a.chertkov@innopolis.ru",
    "name": "Andrey",
    "surname": "Chertkov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 104,
    "email": "an.boiko@innopolis.ru",
    "name": "Anastassiya",
    "surname": "Boiko",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 105,
    "email": "d.konev@innopolis.ru",
    "name": "Dmitry",
    "surname": "Konev",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 107,
    "email": "i.mamedbakov@innopolis.ru",
    "name": "Ilnur",
    "surname": "Mamedbakov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 108,
    "email": "v.solovov@innopolis.ru",
    "name": "Vadim",
    "surname": "Solovov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 109,
    "email": "ar.bykov@innopolis.ru",
    "name": "Bykov",
    "surname": "Artemii",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 111,
    "email": "a.kuznetsova@innopolis.ru",
    "name": "Arina",
    "surname": "Kuznetsova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 112,
    "email": "a.malikova@innopolis.ru",
    "name": "Aygul",
    "surname": "Malikova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 113,
    "email": "m.tkachenko@innopolis.ru",
    "name": "Mikhail",
    "surname": "Tkachenko",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 116,
    "email": "m.lyamets@innopolis.ru",
    "name": "Mikhail",
    "surname": "Lyamets",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 117,
    "email": "ma.popov@innopolis.ru",
    "name": "Maxim",
    "surname": "Popov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 118,
    "email": "o.zufarova@innopolis.ru",
    "name": "Oydinoy",
    "surname": "Zufarova",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 119,
    "email": "t.anufriev@innopolis.ru",
    "name": "Timur",
    "surname": "Anufriyev",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 120,
    "email": "m.burov@innopolis.ru",
    "name": "Maksim",
    "surname": "Burov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 121,
    "email": "e.salihova@innopolis.ru",
    "name": "Elvira",
    "surname": "Salikhova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 123,
    "email": "m.charikova@innopolis.ru",
    "name": "Mariia",
    "surname": "Charikova",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 124,
    "email": "d.ginzburg@innopolis.ru",
    "name": "Danil",
    "surname": "Ginzburg",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 125,
    "email": "p.vybornov@innopolis.ru",
    "name": "Pavel",
    "surname": "Vybornov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 128,
    "email": "s.dinmuhametov@innopolis.ru",
    "name": "Salavat",
    "surname": "Dinmukhametov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 129,
    "email": "a.mullanurov@innopolis.ru",
    "name": "Almir",
    "surname": "Mullanurov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 131,
    "email": "r.mullahmetov@innopolis.ru",
    "name": "Rinat",
    "surname": "Mullakhmetov",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 132,
    "email": "a.rahmatullin@innopolis.ru",
    "name": "Arthur",
    "surname": "Rakhmatullin",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 135,
    "email": "y.yudinskih@innopolis.ru",
    "name": "Yaroslav",
    "surname": "Yudinskikh",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 142,
    "email": "b.fedotov@innopolis.ru",
"name": "Bogdan",
    "surname": "Fedotov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 143,
    "email": "m.mavlyutov@innopolis.ru",
    "name": "Muhammad",
    "surname": "Mavlyutov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 148,
    "email": "s.bogdanik@innopolis.ru",
    "name": "Sergey",
    "surname": "Bogdanik",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 149,
    "email": "l.gabdrahimova@innopolis.ru",
    "name": "Lilya",
    "surname": "Gabdrahimova",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 150,
    "email": "p.nikulin@innopolis.ru",
    "name": "Pavel",
    "surname": "Nikulin",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 151,
    "email": "g.poputnikov@innopolis.ru",
    "name": "George",
    "surname": "Poputnikov",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 152,
    "email": "a.trushin@innopolis.ru",
    "name": "Alexander",
    "surname": "Trushin",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 153,
    "email": "n.kostenko@innopolis.ru",
    "name": "Nikita",
    "surname": "Kostenko",
    "role": "ROLE_PROFESSOR",
    "birthday": "01.01.1999"
},
{
    "id": 156,
    "email": "i.vahula@innopolis.ru",
    "name": "Igor",
    "surname": "Vakhula",
    "role": "ROLE_TA",
    "birthday": "01.01.1999"
},
{
    "id": 161,
    "email": "a.shchetinina@innopolis.ru",
    "name": "Alexandra",
    "surname": "Shchetinina",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
},
{
    "id": 162,
    "email": "d.kabirov@innopolis.ru",
    "name": "Danil",
    "surname": "Kabirov",
    "role": "ROLE_STUDENT",
    "birthday": "01.01.1999"
}
]

var begin_time = '09:00';
var end_time = '10:30';

var myTable = "<table class='table_panel'><tr><td>Name</td>";
myTable += "<td>Check-in</td>";
myTable += "<td>Check-out</td>";
myTable += "<td>"+ "<input class='checkbox' type='checkbox'/>" +"</td></tr>";

for (var i = 0; i < table.length; i++) {
    myTable += "<tr><td>" + table[i].name + " " + table[i].surname + "</td>";
    myTable += "<td>" + "<input value='09:00' type='time' min='0:00' max='23:59'/>" + "</td>";
    myTable += "<td>" + "<input value='10:30' type='time' min='0:00' max='23:59'/>" + "</td>";
    myTable += "<td>"+ "<input class='checkbox' type='checkbox'/>" +"</td></tr>";
}
myTable += "</table>";

document.getElementById('tablePrint').innerHTML = myTable;
