package com.example.sosinventory.sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {

    @Query("SELECT s.product.name, SUM(s.quantity) AS totalQuantity " +
            "FROM Sale s WHERE s.saleDate >= :startDate AND s.saleDate <= :endDate " +
            "GROUP BY s.product.name ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProductThisWeek(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s.product.name, SUM(s.quantity) AS totalQuantity " +
            "FROM Sale s WHERE MONTH(s.saleDate) = :month AND YEAR(s.saleDate) = :year " +
            "GROUP BY s.product.name ORDER BY totalQuantity DESC")
    List<Object[]> findBestSellingProductThisMonth(int month, int year);

    @Query("SELECT s.saleDate, SUM(s.quantity) FROM Sale s WHERE DATE(s.saleDate) = :date GROUP BY s.saleDate")
    List<Object[]> findTotalItemsSoldPerDay(LocalDate date);

    @Query("SELECT s.saleDate, SUM(s.quantity * s.product.price) FROM Sale s WHERE DATE(s.saleDate) = :date GROUP BY s.saleDate")
    List<Object[]> findTotalRevenuePerDay(LocalDate date);

    @Query("SELECT WEEK(s.saleDate), SUM(s.quantity * s.product.price) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate GROUP BY WEEK(s.saleDate)")
    List<Object[]> findTotalRevenuePerWeek(LocalDate startDate, LocalDate endDate);

    @Query("SELECT MONTH(s.saleDate), SUM(s.quantity) FROM Sale s WHERE YEAR(s.saleDate) = :year GROUP BY MONTH(s.saleDate)")
    List<Object[]> findTotalSoldPerMonth(int year);

    @Query("SELECT COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    long findTotalSalesLast7Days(LocalDate startDate, LocalDate endDate);

    List<Sale> findByQuantityLessThan(int threshold);


    List<Sale> findByQuantityGreaterThan(int number);
}
