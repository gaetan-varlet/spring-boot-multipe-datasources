Feature: Calcul des montants totaux de vin

  Scenario: vérifier que le montant calculé est nul quand il n'y a pas de vins en base
    When je calcule le montant total
    Then j'obtiens 0
    Then j'ai 0 ventes pour un motant total de 0

  Scenario: vérifier que le montant calculé est correct avec un résultat entier
    Given les vins suivants
      | chateau | quantite | prix |
      | A       | 2        | 5    |
    When je calcule le montant total
    Then j'obtiens 10
    Then j'ai 1 ventes pour un motant total de 10

  Scenario: vérifier que le montant calculé est correct avec un résultat décimal
    Given les vins suivants
      | chateau | quantite | prix |
      | A       | 2        | 5.1  |
    When je calcule le montant total
    Then j'obtiens 10.2
    Then j'ai 1 ventes pour un motant total de 10.2

  Scenario: vérifier que le montant calculé est correct avec plusieurs vins
    Given les vins suivants
      | chateau | quantite | prix |
      | A       | 2        | 5    |
      | B       | 3        | 2    |
    When je calcule le montant total
    Then j'obtiens 16
    Then j'ai 2 ventes pour un motant total de 16
