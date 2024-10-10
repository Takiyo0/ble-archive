cmake_minimum_required(VERSION 3.28)
project(algopro_quadratic C)

set(CMAKE_C_STANDARD 23)

add_executable(algopro_quadratic
#        socs1-e.c
#        socs1-b.c
#        rproba.c
        "slc/prog 60 case 1.c"
        "slc/prog 60 case 2.c"
        "slc/prog 60 case 3.c"
        "slc/prog 60 case 4.c"
        "slc/prog 60 case 5.c"
        "slc/prog 60 case 1.c"
        "slc/prog 60 case 1.c"
        "slc/prog 90 case 1.c"
        fourthweek/a.c
        fourthweek/b.c
        fourthweek/c.c
        fourthweek/d.c
        fourthweek/e.c
        fourthweek/f.c
        DSCH1/problem1.c
        DSCH1/problem2.c
        DSCH1/problem3.c
        DSCH1/problem4.c
        DSCH1/problem5.c
        DSCH2/problem1.c
        DSCH3/problem1.c
        fifthweek/a.c
        fifthweek/b.c
        fifthweek/c.c
        fifthweek/d.c
        fifthweek/e.c
        fifthweek/f.c
        DSCH4/problem1.c
        #        main.c
#        socs1-a.c
#        socs1-b.c
#        socs1-c.1212c
        #        18924.c
#        playground.c
#        18924.c
#        fastarithmetic.c
#        triplekwek.c
#        duckshistory.c
#        nameparser.c
#        bombandexplosion.c
)
