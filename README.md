# autoPiaTestQuarkus
ISTRUZIONI PER TIRARE SU L'APPLICAZIONE

per il BE
    - apri il terminale nella root del progetto
    - mvn clean install -U (solo la prima volta)
    - mvn quarkus:dev

per il FE
    - Apri il terminale all'interno della cartella del frontend: (\autoPiaTestQuarkus\gestione-apparecchiature) 
    - npm install
    - npm start

apri il browser e naviga su: http://localhost:4200/

Si possono usere 4 utenze: 
    - azienda A può vedere tutto ma solo l'admin può creare un'apparecchiatura
        user - psw: user (azienda A)
        admin - psw: admin (azienda A)
    - azienda B non può vedere nulla né fare nulla
        user2 - psw: user (azienda B)
        admin2 - psw: admin (azienda B)

buona visione :)