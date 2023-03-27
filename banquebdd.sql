-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  lun. 27 mars 2023 à 05:19
-- Version du serveur :  5.7.17
-- Version de PHP :  5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `banquebdd`
--

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`) VALUES
(1, 'Dubois', 'Damien'),
(3, 'Renard', 'Patrice'),
(4, 'Mine', 'Joséphine'),
(5, 'Delfour', 'Justin'),
(6, 'Orly', 'Robert');

-- --------------------------------------------------------

--
-- Structure de la table `compte`
--

CREATE TABLE `compte` (
  `id` int(11) NOT NULL,
  `numero` varchar(50) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL,
  `solde` float DEFAULT '0',
  `decouvert_autorise` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Déchargement des données de la table `compte`
--

INSERT INTO `compte` (`id`, `numero`, `id_client`, `solde`, `decouvert_autorise`) VALUES
(2, '1125', 1, 0, 0),
(3, '1126', 1, 0, 0),
(4, '458', 3, 90.4, 0),
(6, '278', 4, 45.6, 0),
(7, '783', 4, 43.5, 80),
(8, '984', 5, 0, 0),
(9, '4775', 5, 0, 0),
(10, '1554', 6, 0, 0),
(11, '3265', 6, 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `demandevirement`
--

CREATE TABLE `demandevirement` (
  `id` int(11) NOT NULL,
  `compte_source_id` int(11) NOT NULL,
  `compte_dest_id` int(11) NOT NULL,
  `montant` float NOT NULL,
  `acceptee` tinyint(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `demandevirement`
--

INSERT INTO `demandevirement` (`id`, `compte_source_id`, `compte_dest_id`, `montant`, `acceptee`) VALUES
(1, 7, 4, 90.4, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `compte`
--
ALTER TABLE `compte`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKfn9jafk7kn8gpmsxv9uhqna92` (`id_client`);

--
-- Index pour la table `demandevirement`
--
ALTER TABLE `demandevirement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1gldd9sil6l3yoxhnvg8ya6bq` (`compte_dest_id`),
  ADD KEY `FKoltey53w7ypd6vlp91bh3rx6v` (`compte_source_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `compte`
--
ALTER TABLE `compte`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT pour la table `demandevirement`
--
ALTER TABLE `demandevirement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `compte`
--
ALTER TABLE `compte`
  ADD CONSTRAINT `FKfn9jafk7kn8gpmsxv9uhqna92` FOREIGN KEY (`id_client`) REFERENCES `client` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
