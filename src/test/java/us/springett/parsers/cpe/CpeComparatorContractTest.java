/*
 * This file is part of CPE Parser.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) Jeremy Long. All Rights Reserved.
 */
package us.springett.parsers.cpe;

import org.junit.Test;
import us.springett.parsers.cpe.exceptions.CpeValidationException;
import us.springett.parsers.cpe.values.LogicalValue;
import us.springett.parsers.cpe.values.Part;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class CpeComparatorContractTest {

    @Test
    public void testCompareToReflexive() throws CpeValidationException {
        Cpe cpe = new Cpe(Part.APPLICATION, "vendor", "product", "1.0", 
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        assertEquals("Reflexive property violated: x.compareTo(x) should be 0", 
            0, cpe.compareTo(cpe));
    }

    @Test
    public void testCompareToSymmetric() throws CpeValidationException {
        Cpe cpe1 = new Cpe(Part.APPLICATION, "vendor1", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "vendor2", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        int result1 = cpe1.compareTo(cpe2);
        int result2 = cpe2.compareTo(cpe1);
        
        assertEquals("Antisymmetric property violated: sgn(x.compareTo(y)) == -sgn(y.compareTo(x))",
            -Integer.signum(result1), Integer.signum(result2));
    }

    @Test
    public void testCompareToTransitive() throws CpeValidationException {
        Cpe cpe1 = new Cpe(Part.APPLICATION, "vendor1", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "vendor2", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe3 = new Cpe(Part.APPLICATION, "vendor3", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        if (cpe1.compareTo(cpe2) > 0 && cpe2.compareTo(cpe3) > 0) {
            assertTrue("Transitive property violated: if x > y && y > z, then x > z",
                cpe1.compareTo(cpe3) > 0);
        }
    }

    @Test
    public void testCompareToConsistentWithEquals() throws CpeValidationException {
        Cpe cpe1 = new Cpe(Part.APPLICATION, "vendor", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "vendor", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        if (cpe1.equals(cpe2)) {
            assertEquals("compareTo not consistent with equals: equal objects must return 0",
                0, cpe1.compareTo(cpe2));
        }
        
        if (cpe1.compareTo(cpe2) == 0) {
            assertTrue("compareTo not consistent with equals: objects that compareTo 0 should be equal",
                cpe1.equals(cpe2));
        }
    }

    @Test
    public void testTimSortWithManyRandomCpes() throws CpeValidationException {
        List<Cpe> cpes = new ArrayList<>();
        Random random = new Random(12345);
        
        String[] vendors = {"microsoft", "apache", "oracle", "ibm", "google", "amazon", "cisco"};
        String[] products = {"windows", "linux", "java", "python", "nodejs", "tomcat"};
        String[] versions = {"1.0", "2.0", "3.0", "1.0.1", "2.5.3", "10.0", "0.9"};
        
        for (int i = 0; i < 1000; i++) {
            String vendor = vendors[random.nextInt(vendors.length)];
            String product = products[random.nextInt(products.length)];
            String version = versions[random.nextInt(versions.length)];
            
            cpes.add(new Cpe(Part.APPLICATION, vendor, product, version,
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation()));
        }
        
        try {
            Collections.sort(cpes);
        } catch (IllegalArgumentException e) {
            fail("Comparison method violates its general contract: " + e.getMessage());
        }
        
        for (int i = 0; i < cpes.size() - 1; i++) {
            assertTrue("Sorted list violation at index " + i,
                cpes.get(i).compareTo(cpes.get(i + 1)) <= 0);
        }
    }

    @Test
    public void testCompareToWithWellFormedValues() throws CpeValidationException {
        Cpe cpe1 = new Cpe(Part.APPLICATION, "micro\\$oft", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "micro\\$oft", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        assertEquals("CompareTo should return 0 for equal well-formed values",
            0, cpe1.compareTo(cpe2));
        assertTrue("Equals should return true for equal well-formed values",
            cpe1.equals(cpe2));
    }

    @Test
    public void testSortStability() throws CpeValidationException {
        List<Cpe> cpes = new ArrayList<>();
        
        for (int i = 0; i < 100; i++) {
            cpes.add(new Cpe(Part.APPLICATION, "vendor" + (i % 10), "product", "1.0",
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation()));
        }
        
        List<Cpe> sorted1 = new ArrayList<>(cpes);
        Collections.sort(sorted1);
        
        List<Cpe> sorted2 = new ArrayList<>(cpes);
        Collections.sort(sorted2);
        
        assertEquals("Sort should produce consistent results", sorted1, sorted2);
    }

    @Test
    public void testCompareToWithLargeVersionNumbers() throws CpeValidationException {
        Cpe cpe1 = new Cpe(Part.APPLICATION, "acme", "widget", "9",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "acme", "widget", "10",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe3 = new Cpe(Part.APPLICATION, "acme", "widget", "12345678901234567890",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        int cmp12 = cpe1.compareTo(cpe2);
        int cmp23 = cpe2.compareTo(cpe3);
        int cmp13 = cpe1.compareTo(cpe3);
        
        assertTrue("9 should be less than 10", cmp12 < 0);
        assertTrue("10 should be less than 12345678901234567890", cmp23 < 0);
        assertTrue("Transitivity violated: 9 < 10 and 10 < 12345678901234567890, so 9 must be < 12345678901234567890, but got: " 
            + cmp13, cmp13 < 0);
        
        List<Cpe> cpes = Arrays.asList(cpe3, cpe1, cpe2);
        try {
            Collections.sort(cpes);
        } catch (IllegalArgumentException e) {
            fail("Comparison method violates contract with large version numbers: " + e.getMessage());
        }
    }

    @Test
    public void testLargeRandomSortWithVersionVariations() throws CpeValidationException {
        List<Cpe> cpes = new ArrayList<>();
        Random random = new Random(42);
        
        String[] vendors = {"microsoft", "apache", "oracle", "ibm", "google", "amazon", 
                           "cisco", "redhat", "vmware", "adobe"};
        String[] products = {"windows", "linux", "java", "python", "nodejs", "tomcat",
                            "mysql", "postgresql", "redis", "mongodb"};
        
        for (int i = 0; i < 5000; i++) {
            String vendor = vendors[random.nextInt(vendors.length)];
            String product = products[random.nextInt(products.length)];
            
            int major = random.nextInt(20);
            int minor = random.nextInt(50);
            int patch = random.nextInt(100);
            String version = major + "." + minor + "." + patch;
            
            String update = (random.nextInt(10) < 7) ? LogicalValue.ANY.getAbbreviation() : "sp" + random.nextInt(5);
            
            cpes.add(new Cpe(Part.APPLICATION, vendor, product, version, update,
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation()));
        }
        
        try {
            Collections.sort(cpes);
            
            for (int i = 0; i < cpes.size() - 1; i++) {
                int comparison = cpes.get(i).compareTo(cpes.get(i + 1));
                assertTrue("Sorted order violated at index " + i + 
                          ": " + cpes.get(i).toCpe23FS() + 
                          " vs " + cpes.get(i + 1).toCpe23FS(),
                    comparison <= 0);
            }
        } catch (IllegalArgumentException e) {
            fail("Comparison method violates its general contract during sort: " + e.getMessage());
        }
    }

    @Test
    public void testConcurrentSortingThreadSafety() throws InterruptedException {
        int numThreads = 10;
        int iterationsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger failures = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        List<Cpe> sharedCpes = new ArrayList<>();
        try {
            Random random = new Random(123);
            String[] vendors = {"microsoft", "apache", "oracle", "ibm", "google", "amazon", "cisco"};
            String[] products = {"windows", "linux", "java", "python", "nodejs", "tomcat", "mysql"};
            
            for (int i = 0; i < 500; i++) {
                String vendor = vendors[random.nextInt(vendors.length)];
                String product = products[random.nextInt(products.length)];
                int major = random.nextInt(20);
                int minor = random.nextInt(50);
                String version = major + "." + minor;
                
                sharedCpes.add(new Cpe(Part.APPLICATION, vendor, product, version,
                    LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                    LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                    LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
                    LogicalValue.ANY.getAbbreviation()));
            }
        } catch (CpeValidationException e) {
            fail("Failed to create test CPEs: " + e.getMessage());
        }
        
        for (int t = 0; t < numThreads; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < iterationsPerThread; i++) {
                        List<Cpe> copy = new ArrayList<>(sharedCpes);
                        Collections.shuffle(copy);
                        
                        try {
                            Collections.sort(copy);
                            
                            for (int j = 0; j < copy.size() - 1; j++) {
                                if (copy.get(j).compareTo(copy.get(j + 1)) > 0) {
                                    failures.incrementAndGet();
                                    break;
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            if (e.getMessage() != null && e.getMessage().contains("Comparison method violates")) {
                                failures.incrementAndGet();
                            }
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(60, TimeUnit.SECONDS);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        
        assertEquals("Concurrent sorting detected comparison contract violations", 0, failures.get());
    }

    @Test
    public void testConcurrentComparisonConsistency() throws InterruptedException, CpeValidationException {
        int numThreads = 10;
        int comparisonsPerThread = 10000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        AtomicInteger violations = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        Cpe cpe1 = new Cpe(Part.APPLICATION, "vendor1", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe2 = new Cpe(Part.APPLICATION, "vendor2", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        Cpe cpe3 = new Cpe(Part.APPLICATION, "vendor3", "product", "1.0",
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation(), LogicalValue.ANY.getAbbreviation(),
            LogicalValue.ANY.getAbbreviation());
        
        for (int t = 0; t < numThreads; t++) {
            executor.submit(() -> {
                try {
                    for (int i = 0; i < comparisonsPerThread; i++) {
                        int cmp12 = cpe1.compareTo(cpe2);
                        int cmp21 = cpe2.compareTo(cpe1);
                        int cmp23 = cpe2.compareTo(cpe3);
                        int cmp13 = cpe1.compareTo(cpe3);
                        
                        if (Integer.signum(cmp12) != -Integer.signum(cmp21)) {
                            violations.incrementAndGet();
                        }
                        
                        if (cmp12 < 0 && cmp23 < 0 && cmp13 >= 0) {
                            violations.incrementAndGet();
                        }
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(60, TimeUnit.SECONDS);
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        
        assertEquals("Concurrent comparisons violated antisymmetry or transitivity", 0, violations.get());
    }
}
