Version 1.2.0
=============
- supports GWT 2.0, Smartgwt 2.0.0
- added toolstrip panel to window accessible as Toolbar

Version 1.3.0
=============
- supports SmartGWT 2.1.0
- doplnena podpora FLOAT v CTable a CTableDataSource
- model pre tabulku CTableRow je indexovany od 0 v triede ATableServiceImpl
- do ITableColumnDefinition bola doplnena metoda getPkId();
- do CLoggedUserRecord bola doplnena metoda checkRoles(String... rolesToCheck)
- event listenery pracuju len s rozhraniami (IApplicationEvent)
- window manager zobrazuje okna len n okien na jednej vrstve, nasledne zacina zobrazovat opat od zaciatku.
- doplnena podpora ikony v hlavicke okna
- doplnena podpora pre lockovanie tlacidiel v toolbare
