# Simulateur de Chaîne de Transmission - SIT213
Ce programme Java, nommé "Simulateur de Chaîne de Transmission", permet de simuler une chaîne de transmission de données. Cette chaîne comprend une source de données, un ou plusieurs transmetteurs et une destination finale. Le simulateur prend en compte différents paramètres d'exécution, qui peuvent être spécifiés en utilisant des arguments en ligne de commande.

## Installation
Avant d'utiliser le simulateur, assurez-vous d'avoir Java installé sur votre système. Vous pouvez vérifier si Java est installé en ouvrant un terminal (ou une invite de commande) et en exécutant la commande suivante :

`java -version`

Si Java n'est pas installé, téléchargez et installez la dernière version de Java.

Une fois Java installé, suivez ces étapes pour utiliser le simulateur :

Clonez ou téléchargez le dépôt contenant le simulateur sur votre machine.

Ouvrez un terminal (ou une invite de commande) et accédez au répertoire du simulateur à l'aide de la commande cd :

`cd chemin/vers/le/repertoire/simulateur`

Compilez le programme en exécutant le script fourni :

`./compile`

Générez la documentation du code source en exécutant le script genDoc :

`./genDoc`

Pour nettoyer les dossiers **bin** et **docs** il est possible d'utiliser la commande:

`./clearAll`

## Utilisation
Pour exécuter le simulateur, utilisez la commande suivante :

`./simulateur [options]`
où **[options]** représente une liste d'arguments optionnels que vous pouvez utiliser pour personnaliser la simulation. Voici les options disponibles :

`-mess m` : Spécifie le message à transmettre. Vous pouvez utiliser cette option de deux manières :

En utilisant un message binaire de 7 chiffres ou plus (0 ou 1) directement. Par exemple, -mess 1010101.
En spécifiant le nombre de bits du message "aléatoire" à transmettre en utilisant un nombre de 1 à 6 chiffres. Par exemple, -mess 1000 générera un message aléatoire de 1000 bits.

`-s` : Active l'affichage des sondes d'affichage. Si cette option est spécifiée, vous pourrez visualiser les signaux à l'écran pendant la simulation.

`-seed v` : Initialise les générateurs aléatoires avec une valeur v. Cela permet de reproduire les mêmes résultats pour des simulations identiques, car les générateurs aléatoires seront initialisés de la même manière à chaque exécution.

`-form f` : Spécifie le type de formes d'onde à utiliser pour la mise en forme du signal. Les valeurs possibles sont : RZ, NRZ, NRZT. La valeur par défaut est NRZ.

`-nbEch e` : Spécifie le nombre d'échantillons par bit. La valeur par défaut est 30.

`-ampl min max` : Spécifie les valeurs minimale et maximale de l'amplitude du signal. La valeur par défaut est 0.0 1.0.

`-snrpb v` : Spécifie le rapport signal/bruit (SNR). La valeur par défaut est 10.0.

`-ti dt ar` : Ajoute un trajet de décalage dt et d'ampliture ar au signal envoyé. Il est possible d'ajouter jusqu'à 5 trajets au signal envoyé.

`-codeur` : active l'utilisation du codeur et décodeur. Ce dernier transforme les 1 en 101 et les 0 en 010.

## Exemples d'utilisation
Voici quelques exemples d'utilisation du simulateur avec différentes options :

Pour exécuter une simulation avec un message aléatoire de 1000 bits et afficher les sondes d'affichage :

`./simulateur -mess 1000 -s`
Pour exécuter une simulation avec un message personnalisé (1101010) et une initialisation aléatoire des générateurs :

`./simulateur -mess 1101010 -seed 12345`
Pour exécuter une simulation avec un message aléatoire de 500 bits sans affichage des sondes :

`./simulateur -mess 500`
Fonctionnement
Le simulateur construit une chaîne de transmission composée d'une source, de transmetteurs et d'une destination. Le transmetteur utilisé par défaut est un "Transmetteur Parfait Logique". Vous pouvez personnaliser la chaîne de transmission en modifiant le code du simulateur pour utiliser d'autres types de transmetteurs si nécessaire.

La simulation consiste en l'envoi du message à travers la chaîne de transmission, puis le calcul du taux d'erreur binaire (TEB) en comparant les bits du message émis avec ceux du message reçu. Le TEB est un indicateur de la qualité de la transmission.

## Auteur
L'implémentation des classes abstraites de ce programme a été développé par Antoine FAVREL, Louis RAVAUX Benjamin HUGUET, Maëva DONATIEN.
