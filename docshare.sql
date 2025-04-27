-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2025 at 08:08 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `docshare`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Question Papers'),
(3, 'Books');

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
  `id` bigint(20) NOT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `uploaded_at` datetime(6) DEFAULT NULL,
  `approved_by` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `subject_id` bigint(20) DEFAULT NULL,
  `uploaded_by` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`id`, `file_path`, `filename`, `status`, `uploaded_at`, `approved_by`, `category_id`, `subject_id`, `uploaded_by`) VALUES
(1, 'uploads\\4b08447d-dda5-4a42-bf7a-d082a18a90e5_crm new.pdf', 'dsds', 'APPROVED', '2025-04-17 08:06:00.000000', 3, 1, 1, 2),
(2, 'uploads\\01dd3965-d3f3-4827-a7ce-c0cc44dd31eb_crm new.pdf', 'dsa2', 'APPROVED', '2025-04-17 13:38:08.000000', 2, 1, 1, 3),
(3, 'uploads\\5413330b-604c-4da3-a089-46587adea3c5_crm new.pdf', 'c++2', 'APPROVED', '2025-04-17 13:40:44.000000', 2, 3, 3, 3),
(4, 'uploads\\bd26b32f-0793-4abe-b922-1c93a59d3c46_crm new.pdf', 'dsa22', 'APPROVED', '2025-04-17 13:59:45.000000', NULL, 1, 1, 3),
(5, 'uploads\\db4be6a8-1c90-4f4c-859e-1d9fcdd21861_crm new.pdf', 'dasdsad', 'APPROVED', '2025-04-17 14:44:14.000000', NULL, 3, 1, 3),
(6, 'uploads\\60ce2d01-7739-4dff-8e6a-d7c07c910693_crm new.pdf', 'dsastu', 'APPROVED', '2025-04-17 15:19:25.000000', 3, 3, 1, 4),
(7, 'uploads\\def5f306-60db-4d04-bf9d-94c9c4382018_Ecommerce_QuotationhighEnd.pdf', 'c++23', 'REJECTED', '2025-04-17 15:35:42.000000', 2, 3, 3, 4),
(8, 'uploads\\8a572e9e-4fb1-4cd1-b276-8894c0cb4d47_458185243 (1).pdf', 'dsa2', 'REJECTED', '2025-04-17 15:38:13.000000', 2, 1, 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `name`) VALUES
(1, 'DSA12'),
(3, 'C++\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `email`, `status`) VALUES
(2, 'user909812', '$2a$10$J61.w0XonRRYyn3.BhR57OlvKVwts7tlAV3WiQ.vOZ59cthkSxopK', 'TEACHER', 'teacher@gmail.com', 'ACTIVE'),
(3, 'user9098', '$2a$10$7u.i46jlBJw3T772w2IXwuI87I.FePC1XOfJVDRV4LdcQKYCI1PSS', 'ADMIN', 'admin90@gmail.com', 'ACTIVE'),
(4, 'Pritesh Bansal', '$2a$10$1k31yRVcSeprXf91a3UmKOID.tgeooz5Shi8x16KvqarsTOb.CryS', 'STUDENT', 'student@gmail.com', 'ACTIVE'),
(6, 'riya', '$2a$10$KJM9AmTgZD1pU..iPQF6NeZu0Z6pxXU8a2W8S.LM8bpi2JZH4Alvq', 'STUDENT', 'riya@gmail.com', 'ACTIVE');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKiywhancwwpaggthda6bm8auii` (`approved_by`),
  ADD KEY `FK70g21yw6d0n958n3khscvgbls` (`category_id`),
  ADD KEY `FKpby9dar20817f18ipuxmqf4x4` (`subject_id`),
  ADD KEY `FK1ugacya4ssi0ilf8a9tjycgs6` (`uploaded_by`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `subjects`
--
ALTER TABLE `subjects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `FK1ugacya4ssi0ilf8a9tjycgs6` FOREIGN KEY (`uploaded_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK70g21yw6d0n958n3khscvgbls` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  ADD CONSTRAINT `FKiywhancwwpaggthda6bm8auii` FOREIGN KEY (`approved_by`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKpby9dar20817f18ipuxmqf4x4` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
