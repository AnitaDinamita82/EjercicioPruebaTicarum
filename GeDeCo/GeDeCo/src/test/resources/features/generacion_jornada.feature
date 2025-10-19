Feature: Generacion de la Primera Jornada de una competicion

    Como gestor de las competiciones
    Quiero generar la primera jornada de una competicion


    # ====================================================================
    # SCENARIO 01: ASIGNACIÓN COMPLETA (4 Equipos)
    # Competición 101: 4 equipos, 2 pistas -> 2 partidos (Asignación completa)
    # ====================================================================

    Scenario: 01. Asignacion completa de partidos (4 equipos)

        Given existe una Competicion con ID 101, nombre "Liga de Verano de Fútbol 7", deporte "Fútbol", fecha inicio 2025-10-20, fecha fin 2025-10-27, pistas 2 y dias reservados "LUNES,MIERCOLES"
        Given la competicion con ID 101 tiene 4 equipos inscritos
        When intento generar la primera jornada para la competicion con ID 101
        Then debo de obtener una lista de partidosEnJuego
        And y la lista de equiposNoConvocados debe de estar vacia
        And el codigo de estado de la respuesta debe de ser 201

    # ====================================================================
    # SCENARIO 02: ASIGNACIÓN PARCIAL (5 Equipos)
    # Competición 103: 5 equipos, 2 pistas -> 2 partidos (4 equipos) + 1 no convocado
    # ====================================================================
    Scenario: 02. Asignacion incompleta de partidos (5 equipos - un equipo descansa)

        Given existe una Competicion con ID 103, nombre "Torneo de Otoño de Voleibol", deporte "Voleibol", fecha inicio 2025-11-01, fecha fin 2025-11-30, pistas 2 y dias reservados "LUNES,MARTES"
        Given la competicion con ID 103 tiene 5 equipos inscritos
        When intento generar la primera jornada para la competicion con ID 103
        Then debo de obtener una lista de partidosEnJuego
        And y la lista de equiposNoConvocados debe de tener 1 equipo
        And el codigo de estado de la respuesta debe de ser 201

    # ====================================================================
    # SCENARIO 03: ASIGNACION CON EQUIPOS INSUFICIENTES (1 equipos)
    # Competición 102: 1 equipo (solo 203), 1 pista -> ERROR (No hay suficientes equipos para 1 partido)
    # ====================================================================
    Scenario: 03. Asignacion con equipos insuficientes (1 equipo)

        Given existe una Competicion con ID 102, nombre "Copa de Invierno de Baloncesto", deporte "Baloncesto", fecha inicio 2025-11-15, fecha fin 2025-12-20, pistas 1 y dias reservados "SABADO"
        Given la competicion con ID 102 tiene 1 equipos inscritos
        When intento generar la primera jornada para la competicion con ID 102
        Then debo de obtener una lista de partidosEnJuego vacia
        And y la lista de equiposNoConvocados debe de tener 1 equipo
        And el codigo de estado de la respuesta debe de ser 200

    # ====================================================================
    # SCENARIO 04: ERROR: No se puede asignar una fecha
    # Competición 104: Rango de fecha 2025-10-20 a 2025-10-22. Días reservados: JUEVES (fuera del rango).
    # ====================================================================
    Scenario: 04. Error por dia reservado fuera de rango de fechas

        Given existe una Competicion con ID 104, nombre "Liga Rápida de Balonmano", deporte "Balonmano", fecha inicio 2025-10-20, fecha fin 2025-10-22, pistas 1 y dias reservados "JUEVES"
        Given la competicion con ID 104 tiene 2 equipos inscritos
        When intento generar la primera jornada para la competicion con ID 104
        Then debo de obtener una respuesta de error con el mensaje "No hay días hábiles disponibles para la competición 104 - Liga Rápida de Balonmano."
        And el codigo de estado de la respuesta debe de ser 404