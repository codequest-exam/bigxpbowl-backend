package kea.exam.xpbowlingbackend.config;

import kea.exam.xpbowlingbackend.activity.entities.*;
import kea.exam.xpbowlingbackend.activity.repositories.*;
import kea.exam.xpbowlingbackend.operations.Equipment;
import kea.exam.xpbowlingbackend.operations.EquipmentRepository;
import kea.exam.xpbowlingbackend.product.Product;
import kea.exam.xpbowlingbackend.product.ProductRepository;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDay;
import kea.exam.xpbowlingbackend.reservation.competition.CompetitionDayRepository;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservation;
import kea.exam.xpbowlingbackend.reservation.recurring.RecurringBowlingReservationRepository;
import kea.exam.xpbowlingbackend.reservation.Reservation;
import kea.exam.xpbowlingbackend.reservation.ReservationRepository;
import kea.exam.xpbowlingbackend.staff.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class InitData implements CommandLineRunner {
    private final ActivityRepository activityRepository;
    private final ReservationRepository reservationRepository;
    private final RecurringBowlingReservationRepository recurringBowlingReservationRepository;
    private final CompetitionDayRepository competitionDayRepository;
    private final EquipmentRepository equipmentRepository;

    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirhockeyTableRepository airhockeyTableRepository;
    private final DiningTableRepository diningTableRepository;
    private final ProductRepository productRepository;

    private final StaffRepository staffRepository;
    private final ShiftRepository shiftRepository;

    private final List<BowlingLane> bowlingLanes = new ArrayList<>();
    private final List<AirhockeyTable> airhockeyTables = new ArrayList<>();
    private final List<DiningTable> diningTables = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Staff> staff = new ArrayList<>();

    public InitData(ShiftRepository shiftRepository, StaffRepository staffRepository,ProductRepository productRepository ,CompetitionDayRepository competitionDayRepository, RecurringBowlingReservationRepository recurringBowlingReservationRepository, ReservationRepository reservationRepository, BowlingLaneRepository bowlingLaneRepository, AirhockeyTableRepository airhockeyTableRepository, ActivityRepository activityRepository, DiningTableRepository diningTableRepository, EquipmentRepository equipmentRepository) {
        this.reservationRepository = reservationRepository;
        this.activityRepository = activityRepository;
        this.recurringBowlingReservationRepository = recurringBowlingReservationRepository;
        this.competitionDayRepository = competitionDayRepository;
        this.equipmentRepository = equipmentRepository;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airhockeyTableRepository = airhockeyTableRepository;
        this.diningTableRepository = diningTableRepository;
        this.productRepository = productRepository;
        this.staffRepository = staffRepository;
        this.shiftRepository = shiftRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO: Add init data to all repositories
        initCompetitionDays();
        initRecurringReservations();
       initActivities();
        initReservations();
        initEquipment();
        initBowlingLanes();
        initAirhockeyTables();
        initDiningTables();
        initProducts();
        initStaff();
        initShifts();
    }

    private void initStaff() {
        staff.add(new Staff("Laurits", StaffRoles.MANAGER));
        staff.add(new Staff("Henriette", StaffRoles.MANAGER));

        staff.add(new Staff("Mikkel", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Victor", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Liselotte", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Emma", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Sigrid", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Marcus", StaffRoles.EMPLOYEE));
        staff.add(new Staff("Joakim", StaffRoles.EMPLOYEE));

        staff.add(new Staff("Louise", StaffRoles.OPERATOR));
        staff.add(new Staff("Mads", StaffRoles.OPERATOR));

        staffRepository.saveAll(staff);
    }

    private void initShifts() {

        List<Shift> shifts = new ArrayList<>();

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.MONDAY, List.of(staff.get(1), staff.get(3), staff.get(4))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.MONDAY, List.of(staff.get(0), staff.get(5), staff.get(6), staff.get(staff.size()-2))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.TUESDAY, List.of( staff.get(3), staff.get(4), staff.get(staff.size()-1))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.TUESDAY, List.of(staff.get(0), staff.get(1), staff.get(2), staff.get(5))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.WEDNESDAY, List.of(staff.get(1), staff.get(7), staff.get(4))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.WEDNESDAY, List.of(staff.get(0), staff.get(5), staff.get(6), staff.get(8))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.THURSDAY, List.of(staff.get(3), staff.get(4), staff.get(9))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.THURSDAY, List.of(staff.get(0), staff.get(1), staff.get(2), staff.get(5))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.FRIDAY, List.of(staff.get(1), staff.get(3), staff.get(4))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.FRIDAY, List.of(staff.get(0), staff.get(5), staff.get(6), staff.get(8))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.SATURDAY, List.of(staff.get(3), staff.get(4), staff.get(9))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.SATURDAY, List.of(staff.get(0), staff.get(1), staff.get(2), staff.get(5))));

            shifts.add(new Shift(LocalTime.of(10, 0), LocalTime.of(17, 0), DayOfWeek.SUNDAY, List.of(staff.get(1), staff.get(3), staff.get(4))));
            shifts.add(new Shift(LocalTime.of(17, 0), LocalTime.of(23, 0), DayOfWeek.SUNDAY, List.of(staff.get(0), staff.get(5), staff.get(6), staff.get(8))));

        shiftRepository.saveAll(shifts);
    }

    private void initProducts() {
        products.add(new Product("Carlsberg classic", 25, "https://img.goodfon.com/original/4123x2738/0/27/carlsberg-beer-pivo-brend.jpg"));
        products.add(new Product("Coca cola", 20, "https://upload.wikimedia.org/wikipedia/commons/c/cc/Coca-Cola_Local_Brand_Cambodia.jpg"));
        products.add(new Product("Fanta", 20, "https://upload.wikimedia.org/wikipedia/commons/3/3e/Fanta_logo_%282009%29.jpg"));
        products.add(new Product("Sprite", 20, "https://live.staticflickr.com/3775/13174976693_cd23d2fe3d_b.jpg"));
        products.add(new Product("Faxe kondi", 20, "https://upload.wikimedia.org/wikipedia/commons/4/4b/Faxe_Kondi_%28d%C3%A5se%29.jpeg"));
        products.add(new Product("Faxe kondi free", 20, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPTvYi5LwcIwYlRPqPfWNxZ-GfnizLvAt7iw&s"));
        products.add(new Product("Faxe kondi booster", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi power", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi max", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi ultra", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi extreme", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi light", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi zero", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi sugar free", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Faxe kondi sugar", 20, "https://upload.wikimedia.org/wikipedia/commons/9/9a/Pictures_Not_Yet_Available.svg"));
        products.add(new Product("Local red wine", 59, "https://live.staticflickr.com/4392/35968783350_547823ebf1_b.jpg"));
        products.add(new Product("Local white wine", 59, "https://upload.wikimedia.org/wikipedia/commons/8/8d/Glass_of_White_Wine_shot_with_a_bottle_of_white_wine_-_Evan_Swigart.jpg"));
        products.add(new Product("Local rose wine", 59, "https://i1.pickpik.com/photos/910/688/955/rose-table-holidays-tableware-preview.jpg"));
        products.add(new Product("Martini", 79, "https://live.staticflickr.com/4084/5076909064_883d31b1f3_b.jpg"));
        products.add(new Product("Mojito", 79, "https://live.staticflickr.com/4147/5084101811_9f84644414_b.jpg"));

        productRepository.saveAll(products);
    }

    private void initCompetitionDays() {
        List<CompetitionDay> tempDays = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempDays.add(new CompetitionDay(LocalDate.of(2024, 5 + i, 25 + j)));
            }
        }
        competitionDayRepository.saveAll(tempDays);
    }
    private void initEquipment() {
        List<Equipment> equipmentList = Arrays.asList(
                new Equipment("Bowling Shoes (Men)", 50),
                new Equipment("Bowling Shoes (Women)", 50),
                new Equipment("Bowling Balls (Lightweight)", 30),
                new Equipment("Bowling Balls (Mediumweight)", 30),
                new Equipment("Bowling Balls (Heavyweight)", 30),
                new Equipment("Air Hockey Tables", 5),
                new Equipment("Air Hockey Paddles", 20),
                new Equipment("Air Hockey Pucks", 50),
                new Equipment("Dining Tables", 10),
                new Equipment("Dining Chairs", 40)
        );
        equipmentRepository.saveAll(equipmentList);
    }

    private void initRecurringReservations() {
        List<RecurringBowlingReservation> tempReservations = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            for (DayOfWeek day : DayOfWeek.values()) {
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    continue;
                }
                tempReservations.add(new RecurringBowlingReservation(LocalTime.of(10, 0), LocalTime.of(17, 0), day, "12345678", "John Doe", 4));
            }
        }
        recurringBowlingReservationRepository.saveAll(tempReservations);
    }
    public void initActivities() {
        List<Activity> activities = Arrays.asList(
                new Activity(LocalTime.of(10, 0), LocalTime.of(11, 0), LocalDate.now(), ActivityType.BOWLING, 1),
                new Activity(LocalTime.of(11, 0), LocalTime.of(13, 0), LocalDate.now(), ActivityType.DINING, 1),
                new Activity(LocalTime.of(13, 0), LocalTime.of(15, 0), LocalDate.now(), ActivityType.AIRHOCKEY, 1),
                new Activity(LocalTime.of(15, 0), LocalTime.of(17, 0), LocalDate.now(), ActivityType.BOWLING, 1),
                new Activity(LocalTime.of(17, 0), LocalTime.of(19, 0), LocalDate.now(), ActivityType.DINING, 1),
                new Activity(LocalTime.of(19, 0), LocalTime.of(21, 0), LocalDate.now(), ActivityType.AIRHOCKEY, 1)
        );

        activityRepository.saveAll(activities);
    }
    public void initReservations() {
        List<Activity> activities = activityRepository.findAll();
        Random random = new Random();

        Reservation reservation1 = new Reservation();
        reservation1.setPhoneNumber("123-456-7891");
        reservation1.setName("Alice Johnson");
        reservation1.setParticipants(random.nextInt(5) + 1);
        reservation1.setActivities(activities.subList(0, 2));

        Reservation reservation2 = new Reservation();
        reservation2.setPhoneNumber("123-456-7892");
        reservation2.setName("Bob Smith");
        reservation2.setParticipants(random.nextInt(5) + 1);
        reservation2.setActivities(activities.subList(2, 4));

        Reservation reservation3 = new Reservation();
        reservation3.setPhoneNumber("123-456-7893");
        reservation3.setName("Charlie Brown");
        reservation3.setParticipants(random.nextInt(5) + 1);
        reservation3.setActivities(activities.subList(4, 6));


        List<Reservation> reservations = Arrays.asList(reservation1, reservation2, reservation3);
        reservationRepository.saveAll(reservations);
    }

    private void initBowlingLanes() {
        List<BowlingLane> tempLanes = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            tempLanes.add(new BowlingLane(false, i > 19, i + 1, i > 19 ? ActivityType.CHILDBOWLING : ActivityType.BOWLING));
        }
        bowlingLanes.addAll(bowlingLaneRepository.saveAll(tempLanes));
    }

    private void initAirhockeyTables() {
        List<AirhockeyTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            tempTables.add(new AirhockeyTable(false, i + 1));
        }
        airhockeyTables.addAll(airhockeyTableRepository.saveAll(tempTables));
    }

    private void initDiningTables() {
        List<DiningTable> tempTables = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tempTables.add(new DiningTable(false, i + 1));
        }
        diningTables.addAll(diningTableRepository.saveAll(tempTables));
    }
}




