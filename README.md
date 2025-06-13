# Panier E-commerce

Application Spring Boot pour calculer le prix d'un panier selon le type de client.

## Description

Cette application gère un système de panier pour une boutique en ligne qui vend 3 types de produits :
- Téléphones haut de gamme
- Téléphones milieu de gamme  
- Ordinateurs portables

Il existe 2 types de clients avec des tarifs différents :
- **Particuliers** : tarifs standards
- **Professionnels** : tarifs réduits selon le chiffre d'affaires
  - CA < 10M€ : remise modérée
  - CA ≥ 10M€ : remise importante

## Tarifs

**Téléphone haut gamme :**
- Particulier : 1500€
- Professionnel < 10M€ : 1150€  
- Professionnel ≥ 10M€ : 1000€

**Téléphone milieu gamme :**
- Particulier : 800€
- Professionnel < 10M€ : 600€
- Professionnel ≥ 10M€ : 550€

**Laptop :**
- Particulier : 1200€
- Professionnel < 10M€ : 1000€
- Professionnel ≥ 10M€ : 900€

## Installation

```bash
mvn spring-boot:run
```

L'application démarre et affiche automatiquement un exemple de calcul de panier pour les 3 types de clients.

## Tests

```bash
mvn test
```

Les tests incluent :
- Tests unitaires pour chaque service et repository
- **Test d'intégration** (`RepositoryIntegrationTest`) qui vérifie la cohérence entre tous les fichiers JSON

## Configuration

Les prix sont configurés dans les fichiers JSON du dossier `src/main/resources/data/` :
- `typeclients.json` : définition des types de clients
- `typeproduits.json` : définition des types de produits
- `tarifications.json` : grille tarifaire

Pour modifier les prix, il suffit d'éditer le fichier `tarifications.json`.

## Structure

```
src/main/java/com/ecommerce/panier/
├── model/          # Classes métier (Client, Panier, etc.)
├── repository/     # Accès aux données JSON
├── service/        # Logique de calcul des prix
└── runner/         # Démonstration
```

## Exemple d'utilisation

Le `DemoRunner` crée automatiquement un panier avec :
- 1 téléphone haut de gamme
- 2 téléphones milieu de gamme
- 3 laptops

Et calcule le prix pour chaque type de client.

## Technologies

- Spring Boot
- Jackson (pour JSON)
- JUnit (tests)