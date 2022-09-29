-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 29, 2022 at 06:07 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aniflix`
--

-- --------------------------------------------------------

--
-- Table structure for table `area_laboral`
--

CREATE TABLE `area_laboral` (
  `id_departamento` int(11) NOT NULL,
  `departamento` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `area_laboral`
--

INSERT INTO `area_laboral` (`id_departamento`, `departamento`, `estado`) VALUES
(1, 'Administracion', 1),
(2, 'Desarrollo', 1),
(3, 'Contabilidad', 1),
(4, 'Diseño', 1);

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `cliente` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `contrasena` varchar(20) NOT NULL,
  `plan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `usuario`, `cliente`, `correo`, `contrasena`, `plan`) VALUES
(1, 'ems_garcia', 'Emida Garcia', 'emygarcia@correo.com', 'perrito123', 'Forever Alone Premiun');

-- --------------------------------------------------------

--
-- Table structure for table `empleado`
--

CREATE TABLE `empleado` (
  `id_empleado` int(11) NOT NULL,
  `cui` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `salario` int(11) NOT NULL,
  `departamento` varchar(50) NOT NULL,
  `correo` varchar(250) NOT NULL,
  `contrasena` varchar(20) NOT NULL,
  `fecha_ingreso` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_egreso` timestamp NULL DEFAULT NULL,
  `rol` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `empleado`
--

INSERT INTO `empleado` (`id_empleado`, `cui`, `nombre`, `salario`, `departamento`, `correo`, `contrasena`, `fecha_ingreso`, `fecha_egreso`, `rol`) VALUES
(1, 1234567890, 'Fernando Estrada', 5000, 'Administrador', 'fernandoestrada@aniflix.com', 'contrasenia', '2004-12-12 06:00:00', NULL, 1),
(2, 987654321, 'Jose Pereira', 6000, 'Desarrollador', 'josepereira@aniflix.com', 'pass234', '2022-09-23 16:42:13', NULL, 0),
(3, 123, 'Juan Penas', 1400, 'Administracion', 'juan.penas@aniflix.com', '123', '2010-01-01 06:00:00', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `empleado_evento`
--

CREATE TABLE `empleado_evento` (
  `id` int(10) UNSIGNED NOT NULL,
  `empleado_id` int(10) UNSIGNED NOT NULL,
  `evento_id` int(10) UNSIGNED NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `empleado_evento`
--

INSERT INTO `empleado_evento` (`id`, `empleado_id`, `evento_id`, `fecha`) VALUES
(1, 2, 1, '2022-09-21 00:17:47'),
(2, 2, 2, '2022-09-21 00:19:09'),
(3, 2, 1, '2022-09-23 16:41:00'),
(4, 2, 2, '2022-09-23 16:41:34'),
(5, 2, 1, '2022-09-27 14:13:39'),
(6, 2, 2, '2022-09-27 14:14:03'),
(8, 2, 1, '2022-09-27 14:15:19'),
(9, 2, 2, '2022-09-27 14:15:21'),
(10, 1, 1, '2022-09-27 18:30:18'),
(11, 1, 2, '2022-09-27 18:30:31'),
(12, 1, 1, '2022-09-27 18:32:55'),
(13, 1, 2, '2022-09-27 18:34:03'),
(14, 1, 1, '2022-09-27 18:35:28'),
(15, 1, 2, '2022-09-27 18:35:57'),
(20, 3, 1, '2022-09-29 03:53:44');

-- --------------------------------------------------------

--
-- Table structure for table `evento`
--

CREATE TABLE `evento` (
  `id_evento` int(11) NOT NULL,
  `tipo` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `evento`
--

INSERT INTO `evento` (`id_evento`, `tipo`) VALUES
(1, 'Entrada'),
(2, 'Salida');

-- --------------------------------------------------------

--
-- Table structure for table `plan`
--

CREATE TABLE `plan` (
  `id_plan` int(11) NOT NULL,
  `plan` varchar(100) NOT NULL,
  `precio_usd` int(11) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `plan`
--

INSERT INTO `plan` (`id_plan`, `plan`, `precio_usd`, `estado`) VALUES
(1, 'Forever Alone', 0, 1),
(2, 'Forever Alone Premiun', 2, 1),
(3, 'Otaku Couple', 4, 1),
(4, 'Aniflix Family Premiun', 15, 0),
(6, 'Otaku Family', 8, 1);

-- --------------------------------------------------------

--
-- Table structure for table `servidor`
--

CREATE TABLE `servidor` (
  `id_servidor` int(11) NOT NULL,
  `servidor` varchar(50) NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `servidor`
--

INSERT INTO `servidor` (`id_servidor`, `servidor`, `estado`) VALUES
(1, 'Japón', 1),
(2, 'Portugal', 1),
(3, 'Guatemala', 1),
(4, 'España', 1),
(5, 'Brasil', 1),
(6, 'Argentina', 1),
(10, 'Italia', 0),
(11, 'Filipinas', 1),
(12, 'Salvador', 1),
(13, 'Brasil', 1),
(14, 'Alemania', 1),
(15, 'Reino Unido', 1),
(16, 'Costa Rica', 0),
(17, 'Chile', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `area_laboral`
--
ALTER TABLE `area_laboral`
  ADD PRIMARY KEY (`id_departamento`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Indexes for table `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id_empleado`);

--
-- Indexes for table `empleado_evento`
--
ALTER TABLE `empleado_evento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `empleado_id` (`empleado_id`) USING BTREE,
  ADD KEY `evento_id` (`evento_id`);

--
-- Indexes for table `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id_evento`);

--
-- Indexes for table `plan`
--
ALTER TABLE `plan`
  ADD PRIMARY KEY (`id_plan`);

--
-- Indexes for table `servidor`
--
ALTER TABLE `servidor`
  ADD PRIMARY KEY (`id_servidor`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `area_laboral`
--
ALTER TABLE `area_laboral`
  MODIFY `id_departamento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id_empleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `empleado_evento`
--
ALTER TABLE `empleado_evento`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `evento`
--
ALTER TABLE `evento`
  MODIFY `id_evento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `plan`
--
ALTER TABLE `plan`
  MODIFY `id_plan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `servidor`
--
ALTER TABLE `servidor`
  MODIFY `id_servidor` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
