package com.enyoi.ragquery.ia.infrastructure.data;

import java.util.List;
import java.util.Map;

// A esta clase no se le hacen pruebas unitarias es nuestro homologo de los textos
// o documentos que vamos a meter en la base de datos de conocimiento
public final class SolidArchitectureDocuments {

    private SolidArchitectureDocuments() {
        // Utility class
    }

    public static List<DocumentData> getAllDocuments() {
        return List.of(
                // SOLID Principles
                getSRPDocument(),
                getOCPDocument(),
                getLSPDocument(),
                getISPDocument(),
                getDIPDocument(),

                // Hexagonal Architecture
                getHexagonalOverviewDocument(),
                getHexagonalPortsDocument(),
                getHexagonalAdaptersDocument(),
                getHexagonalDomainDocument(),
                getHexagonalApplicationDocument(),

                // Clean Architecture
                getCleanArchitectureOverviewDocument(),
                getCleanArchitectureLayersDocument(),

                // Practical Examples
                getPracticalExamplesDocument()
        );
    }

    public record DocumentData(String content, Map<String, Object> metadata) {}

    // ==================== SOLID Principles ====================

    private static DocumentData getSRPDocument() {
        String content = """
            # Principio de Responsabilidad Única (SRP)
            
            El Principio de Responsabilidad Única (Single Responsibility Principle) establece que 
            una clase debe tener una, y solo una, razón para cambiar. Esto significa que una clase 
            debe tener solo una responsabilidad o trabajo.
            
            ## ¿Por qué es importante?
            
            - **Facilita el mantenimiento**: Cuando una clase tiene una sola responsabilidad, 
              los cambios en esa funcionalidad solo afectan a esa clase.
            - **Mejora la legibilidad**: El código es más fácil de entender cuando cada clase 
              hace una cosa específica.
            - **Facilita las pruebas**: Las clases con una sola responsabilidad son más fáciles de probar.
            - **Reduce el acoplamiento**: Las clases con responsabilidades claras tienen menos dependencias.
            
            ## Ejemplo Malo (Viola SRP)
            
            ```java
            public class Employee {
                public void calculatePay() { /* Lógica de nómina */ }
                public void saveToDatabase() { /* Lógica de persistencia */ }
                public void generateReport() { /* Lógica de reportes */ }
            }
            ```
            
            Esta clase tiene tres razones para cambiar: cambios en cálculos de nómina, 
            cambios en la base de datos, y cambios en el formato de reportes.
            
            ## Ejemplo Bueno (Cumple SRP)
            
            ```java
            public class Employee {
                private String name;
                private BigDecimal salary;
                // Solo datos del empleado
            }
            
            public class PayrollCalculator {
                public BigDecimal calculatePay(Employee employee) { /* Solo cálculos */ }
            }
            
            public class EmployeeRepository {
                public void save(Employee employee) { /* Solo persistencia */ }
            }
            
            public class EmployeeReportGenerator {
                public Report generate(Employee employee) { /* Solo reportes */ }
            }
            ```
            
            Ahora cada clase tiene una sola razón para cambiar.
            """;

        return new DocumentData(content, Map.of(
                "source", "SOLID - SRP",
                "topic", "Single Responsibility Principle",
                "category", "SOLID"
        ));
    }

    private static DocumentData getOCPDocument() {
        String content = """
            # Principio de Abierto/Cerrado (OCP)
            
            El Principio de Abierto/Cerrado (Open/Closed Principle) establece que las entidades 
            de software (clases, módulos, funciones) deben estar abiertas para extensión, 
            pero cerradas para modificación.
            
            ## ¿Qué significa?
            
            - **Abierto para extensión**: Podemos agregar nuevo comportamiento.
            - **Cerrado para modificación**: No necesitamos modificar el código existente.
            
            ## ¿Cómo lograrlo?
            
            Usando abstracciones (interfaces, clases abstractas) y polimorfismo.
            
            ## Ejemplo Malo (Viola OCP)
            
            ```java
            public class DiscountCalculator {
                public double calculate(String customerType, double amount) {
                    if (customerType.equals("regular")) {
                        return amount * 0.1;
                    } else if (customerType.equals("premium")) {
                        return amount * 0.2;
                    } else if (customerType.equals("vip")) {
                        return amount * 0.3;
                    }
                    // Cada nuevo tipo requiere modificar esta clase
                    return 0;
                }
            }
            ```
            
            ## Ejemplo Bueno (Cumple OCP)
            
            ```java
            public interface DiscountStrategy {
                double calculate(double amount);
            }
            
            public class RegularDiscount implements DiscountStrategy {
                public double calculate(double amount) { return amount * 0.1; }
            }
            
            public class PremiumDiscount implements DiscountStrategy {
                public double calculate(double amount) { return amount * 0.2; }
            }
            
            public class VIPDiscount implements DiscountStrategy {
                public double calculate(double amount) { return amount * 0.3; }
            }
            
            public class DiscountCalculator {
                public double calculate(DiscountStrategy strategy, double amount) {
                    return strategy.calculate(amount);
                }
            }
            ```
            
            Ahora para agregar un nuevo tipo de descuento, solo creamos una nueva clase 
            que implemente DiscountStrategy, sin modificar el código existente.
            """;

        return new DocumentData(content, Map.of(
                "source", "SOLID - OCP",
                "topic", "Open/Closed Principle",
                "category", "SOLID"
        ));
    }

    private static DocumentData getLSPDocument() {
        String content = """
            # Principio de Sustitución de Liskov (LSP)
            
            El Principio de Sustitución de Liskov establece que los objetos de una clase 
            derivada deben poder sustituir objetos de la clase base sin alterar el 
            comportamiento correcto del programa.
            
            ## La regla simple
            
            Si S es un subtipo de T, entonces los objetos de tipo T pueden ser reemplazados 
            por objetos de tipo S sin alterar ninguna de las propiedades deseables del programa.
            
            ## Ejemplo Clásico Malo: El Rectángulo y el Cuadrado
            
            ```java
            public class Rectangle {
                protected int width;
                protected int height;
                
                public void setWidth(int width) { this.width = width; }
                public void setHeight(int height) { this.height = height; }
                public int getArea() { return width * height; }
            }
            
            public class Square extends Rectangle {
                @Override
                public void setWidth(int width) {
                    this.width = width;
                    this.height = width; // ¡Viola LSP!
                }
                
                @Override
                public void setHeight(int height) {
                    this.width = height; // ¡Viola LSP!
                    this.height = height;
                }
            }
            ```
            
            El problema es que un Square no se comporta como un Rectangle:
            
            ```java
            Rectangle rect = new Square();
            rect.setWidth(5);
            rect.setHeight(10);
            // Esperamos área = 50, pero obtenemos 100
            ```
            
            ## Solución que cumple LSP
            
            ```java
            public interface Shape {
                int getArea();
            }
            
            public class Rectangle implements Shape {
                private final int width;
                private final int height;
                
                public Rectangle(int width, int height) {
                    this.width = width;
                    this.height = height;
                }
                
                public int getArea() { return width * height; }
            }
            
            public class Square implements Shape {
                private final int side;
                
                public Square(int side) { this.side = side; }
                
                public int getArea() { return side * side; }
            }
            ```
            
            ## Señales de violación de LSP
            
            - Métodos vacíos o que lanzan excepciones en clases derivadas
            - Verificaciones de tipo con instanceof
            - Comentarios que indican "no usar en subclases"
            """;

        return new DocumentData(content, Map.of(
                "source", "SOLID - LSP",
                "topic", "Liskov Substitution Principle",
                "category", "SOLID"
        ));
    }

    private static DocumentData getISPDocument() {
        String content = """
            # Principio de Segregación de Interfaces (ISP)
            
            El Principio de Segregación de Interfaces establece que ningún cliente debe 
            estar obligado a depender de métodos que no utiliza. Es mejor tener muchas 
            interfaces pequeñas y específicas que una interfaz grande y general.
            
            ## El problema de las interfaces gordas
            
            Cuando una interfaz tiene demasiados métodos, las clases que la implementan 
            se ven obligadas a implementar métodos que no necesitan.
            
            ## Ejemplo Malo (Viola ISP)
            
            ```java
            public interface Worker {
                void work();
                void eat();
                void sleep();
                void attendMeeting();
                void writeReport();
            }
            
            public class Robot implements Worker {
                public void work() { /* OK */ }
                public void eat() { throw new UnsupportedOperationException(); } // ¡No come!
                public void sleep() { throw new UnsupportedOperationException(); } // ¡No duerme!
                public void attendMeeting() { /* OK */ }
                public void writeReport() { /* OK */ }
            }
            ```
            
            ## Ejemplo Bueno (Cumple ISP)
            
            ```java
            public interface Workable {
                void work();
            }
            
            public interface Feedable {
                void eat();
            }
            
            public interface Sleepable {
                void sleep();
            }
            
            public interface MeetingAttendee {
                void attendMeeting();
            }
            
            public class Human implements Workable, Feedable, Sleepable, MeetingAttendee {
                public void work() { /* ... */ }
                public void eat() { /* ... */ }
                public void sleep() { /* ... */ }
                public void attendMeeting() { /* ... */ }
            }
            
            public class Robot implements Workable, MeetingAttendee {
                public void work() { /* ... */ }
                public void attendMeeting() { /* ... */ }
            }
            ```
            
            ## Beneficios de ISP
            
            - Código más flexible y mantenible
            - Clases más cohesivas
            - Facilita el testing (mocks más simples)
            - Reduce el acoplamiento
            """;

        return new DocumentData(content, Map.of(
                "source", "SOLID - ISP",
                "topic", "Interface Segregation Principle",
                "category", "SOLID"
        ));
    }

    private static DocumentData getDIPDocument() {
        String content = """
            # Principio de Inversión de Dependencias (DIP)
            
            El Principio de Inversión de Dependencias establece que:
            1. Los módulos de alto nivel no deben depender de módulos de bajo nivel. 
               Ambos deben depender de abstracciones.
            2. Las abstracciones no deben depender de los detalles. 
               Los detalles deben depender de las abstracciones.
            
            ## Sin DIP (acoplamiento fuerte)
            
            ```java
            public class OrderService {
                private MySQLDatabase database = new MySQLDatabase(); // Dependencia directa
                private EmailSender emailSender = new EmailSender();  // Dependencia directa
                
                public void createOrder(Order order) {
                    database.save(order);
                    emailSender.sendConfirmation(order);
                }
            }
            ```
            
            Problemas:
            - No puedes cambiar la base de datos sin modificar OrderService
            - Difícil de testear (necesitas una base de datos real)
            - Alto acoplamiento
            
            ## Con DIP (inversión de dependencias)
            
            ```java
            // Abstracciones
            public interface OrderRepository {
                void save(Order order);
            }
            
            public interface NotificationService {
                void sendConfirmation(Order order);
            }
            
            // Módulo de alto nivel
            public class OrderService {
                private final OrderRepository repository;
                private final NotificationService notifications;
                
                public OrderService(OrderRepository repository, NotificationService notifications) {
                    this.repository = repository;
                    this.notifications = notifications;
                }
                
                public void createOrder(Order order) {
                    repository.save(order);
                    notifications.sendConfirmation(order);
                }
            }
            
            // Implementaciones (detalles)
            public class MySQLOrderRepository implements OrderRepository {
                public void save(Order order) { /* MySQL logic */ }
            }
            
            public class EmailNotificationService implements NotificationService {
                public void sendConfirmation(Order order) { /* Email logic */ }
            }
            ```
            
            ## Inyección de Dependencias
            
            DIP se implementa comúnmente con Inyección de Dependencias (DI), donde las 
            dependencias se "inyectan" desde fuera en lugar de crearse dentro de la clase.
            
            Formas de inyección:
            - **Constructor**: Más recomendada, dependencias explícitas
            - **Setter**: Para dependencias opcionales
            - **Campo**: Usada por frameworks como Spring (@Autowired)
            
            En Spring Boot, usamos @Service, @Repository, @Component junto con @Autowired 
            o constructor injection para implementar DIP automáticamente.
            """;

        return new DocumentData(content, Map.of(
                "source", "SOLID - DIP",
                "topic", "Dependency Inversion Principle",
                "category", "SOLID"
        ));
    }

    // ==================== Hexagonal Architecture ====================

    private static DocumentData getHexagonalOverviewDocument() {
        String content = """
            # Arquitectura Hexagonal (Ports and Adapters)
            
            La Arquitectura Hexagonal, también conocida como "Ports and Adapters", fue 
            propuesta por Alistair Cockburn. Su objetivo principal es crear aplicaciones 
            que sean independientes de frameworks, bases de datos y interfaces de usuario.
            
            ## Concepto Central
            
            La aplicación se visualiza como un hexágono donde:
            - El **núcleo** (dominio) está en el centro
            - Los **puertos** son las interfaces del núcleo
            - Los **adaptadores** conectan el mundo exterior con los puertos
            
            ## Principios Fundamentales
            
            1. **Separación de responsabilidades**: El dominio no conoce el mundo exterior
            2. **Inversión de dependencias**: El dominio define interfaces (puertos), 
               la infraestructura las implementa (adaptadores)
            3. **Testabilidad**: El dominio se puede probar sin infraestructura
            
            ## Estructura Típica
            
            ```
            src/main/java/
            ├── domain/              # Núcleo de la aplicación
            │   ├── model/           # Entidades y Value Objects
            │   └── port/            # Interfaces (puertos)
            │       ├── input/       # Puertos de entrada (casos de uso)
            │       └── output/      # Puertos de salida (repositorios, servicios externos)
            │
            ├── application/         # Casos de uso (orquestadores)
            │
            └── infrastructure/      # Adaptadores
                ├── input/           # Adaptadores de entrada (REST, CLI, etc.)
                └── output/          # Adaptadores de salida (BD, APIs externas)
            ```
            
            ## Flujo de una petición
            
            1. Una petición HTTP llega al adaptador de entrada (Controller)
            2. El controller llama al caso de uso (Application)
            3. El caso de uso usa el dominio y los puertos de salida
            4. Los adaptadores de salida implementan la persistencia
            5. La respuesta fluye de vuelta
            
            ## Beneficios
            
            - Independencia del framework
            - Testabilidad del dominio sin infraestructura
            - Facilidad para cambiar bases de datos o APIs
            - Clara separación entre lógica de negocio e infraestructura
            """;

        return new DocumentData(content, Map.of(
                "source", "Hexagonal Architecture - Overview",
                "topic", "Arquitectura Hexagonal",
                "category", "Architecture"
        ));
    }

    private static DocumentData getHexagonalPortsDocument() {
        String content = """
            # Puertos en Arquitectura Hexagonal
            
            Los puertos son interfaces que definen cómo el núcleo de la aplicación 
            interactúa con el mundo exterior. Son contratos que deben ser implementados 
            por los adaptadores.
            
            ## Tipos de Puertos
            
            ### Puertos de Entrada (Driving/Primary Ports)
            
            Definen las operaciones que la aplicación ofrece al mundo exterior.
            Son los "casos de uso" de la aplicación.
            
            ```java
            // Puerto de entrada - Define qué operaciones ofrece la aplicación
            public interface CreateOrderUseCase {
                OrderId createOrder(CreateOrderCommand command);
            }
            
            public interface GetOrderUseCase {
                Order getOrder(OrderId id);
            }
            ```
            
            ### Puertos de Salida (Driven/Secondary Ports)
            
            Definen las operaciones que la aplicación necesita del mundo exterior
            (persistencia, APIs externas, notificaciones, etc.)
            
            ```java
            // Puerto de salida - Define qué necesita la aplicación
            public interface OrderRepository {
                void save(Order order);
                Optional<Order> findById(OrderId id);
                List<Order> findByCustomer(CustomerId customerId);
            }
            
            public interface PaymentGateway {
                PaymentResult processPayment(Payment payment);
            }
            
            public interface NotificationSender {
                void sendOrderConfirmation(Order order, Customer customer);
            }
            ```
            
            ## Características de buenos puertos
            
            1. **Expresan intención de negocio**: No términos técnicos
            2. **Son estables**: Cambian solo cuando cambia el negocio
            3. **No exponen detalles de implementación**: No filtran tecnología
            4. **Son pequeños y enfocados**: Principio ISP
            
            ## Ejemplo: Puerto vs Implementación
            
            ```java
            // Puerto (en domain/port)
            public interface ProductCatalog {
                List<Product> searchProducts(String query);
                Product getProductDetails(ProductId id);
            }
            
            // Adaptador (en infrastructure/output)
            @Repository
            public class ElasticsearchProductCatalog implements ProductCatalog {
                private final ElasticsearchClient client;
                
                @Override
                public List<Product> searchProducts(String query) {
                    // Implementación específica de Elasticsearch
                }
                
                @Override
                public Product getProductDetails(ProductId id) {
                    // Implementación específica de Elasticsearch
                }
            }
            ```
            
            El dominio solo conoce ProductCatalog, no sabe que se usa Elasticsearch.
            """;

        return new DocumentData(content, Map.of(
                "source", "Hexagonal Architecture - Ports",
                "topic", "Puertos en Arquitectura Hexagonal",
                "category", "Architecture"
        ));
    }

    private static DocumentData getHexagonalAdaptersDocument() {
        String content = """
            # Adaptadores en Arquitectura Hexagonal
            
            Los adaptadores son implementaciones concretas que conectan los puertos 
            con el mundo exterior. Traducen entre el lenguaje del dominio y las 
            tecnologías específicas.
            
            ## Tipos de Adaptadores
            
            ### Adaptadores de Entrada (Driving Adapters)
            
            Reciben peticiones del exterior y las traducen a llamadas al dominio.
            
            ```java
            // Adaptador REST
            @RestController
            @RequestMapping("/api/orders")
            public class OrderController {
                
                private final CreateOrderUseCase createOrderUseCase;
                private final GetOrderUseCase getOrderUseCase;
                
                // Traduce HTTP a llamadas del dominio
                @PostMapping
                public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
                    CreateOrderCommand command = mapToCommand(request);
                    OrderId orderId = createOrderUseCase.createOrder(command);
                    return ResponseEntity.ok(new OrderResponse(orderId));
                }
            }
            
            // Adaptador CLI
            @Component
            public class OrderCLI implements CommandLineRunner {
                private final CreateOrderUseCase createOrderUseCase;
                
                @Override
                public void run(String... args) {
                    // Traduce argumentos CLI a llamadas del dominio
                }
            }
            
            // Adaptador de mensajería
            @Component
            public class OrderMessageListener {
                private final CreateOrderUseCase createOrderUseCase;
                
                @RabbitListener(queues = "orders")
                public void handleOrderMessage(OrderMessage message) {
                    // Traduce mensaje a llamadas del dominio
                }
            }
            ```
            
            ### Adaptadores de Salida (Driven Adapters)
            
            Implementan los puertos de salida conectando con tecnologías específicas.
            
            ```java
            // Adaptador de persistencia con JPA
            @Repository
            public class JpaOrderRepository implements OrderRepository {
                
                private final SpringDataOrderRepository springDataRepository;
                private final OrderMapper mapper;
                
                @Override
                public void save(Order order) {
                    OrderEntity entity = mapper.toEntity(order);
                    springDataRepository.save(entity);
                }
                
                @Override
                public Optional<Order> findById(OrderId id) {
                    return springDataRepository.findById(id.getValue())
                        .map(mapper::toDomain);
                }
            }
            
            // Adaptador de API externa
            @Component
            public class StripePaymentGateway implements PaymentGateway {
                
                private final StripeClient stripeClient;
                
                @Override
                public PaymentResult processPayment(Payment payment) {
                    StripePaymentIntent intent = stripeClient.createPaymentIntent(
                        payment.getAmount(),
                        payment.getCurrency()
                    );
                    return mapToPaymentResult(intent);
                }
            }
            ```
            
            ## Responsabilidades del Adaptador
            
            1. **Traducción**: Convierte entre formatos externos y del dominio
            2. **Manejo de errores**: Traduce excepciones técnicas a excepciones de dominio
            3. **Configuración**: Gestiona detalles de conexión y configuración
            4. **Logging y métricas**: Puede agregar observabilidad
            """;

        return new DocumentData(content, Map.of(
                "source", "Hexagonal Architecture - Adapters",
                "topic", "Adaptadores en Arquitectura Hexagonal",
                "category", "Architecture"
        ));
    }

    private static DocumentData getHexagonalDomainDocument() {
        String content = """
            # Dominio en Arquitectura Hexagonal
            
            El dominio es el corazón de la aplicación. Contiene la lógica de negocio pura, 
            sin dependencias de frameworks, bases de datos o interfaces de usuario.
            
            ## Componentes del Dominio
            
            ### Entidades (Entities)
            
            Objetos con identidad única que persisten en el tiempo.
            
            ```java
            public class Order {
                private final OrderId id;
                private final CustomerId customerId;
                private OrderStatus status;
                private final List<OrderLine> lines;
                private Money total;
                
                // La entidad contiene lógica de negocio
                public void addLine(Product product, int quantity) {
                    if (status != OrderStatus.DRAFT) {
                        throw new OrderAlreadyProcessedException(id);
                    }
                    lines.add(new OrderLine(product, quantity));
                    recalculateTotal();
                }
                
                public void confirm() {
                    if (lines.isEmpty()) {
                        throw new EmptyOrderException(id);
                    }
                    this.status = OrderStatus.CONFIRMED;
                }
            }
            ```
            
            ### Value Objects
            
            Objetos sin identidad, definidos por sus atributos. Son inmutables.
            
            ```java
            public record Money(BigDecimal amount, Currency currency) {
                
                public Money {
                    if (amount.compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException("Amount cannot be negative");
                    }
                }
                
                public Money add(Money other) {
                    if (!this.currency.equals(other.currency)) {
                        throw new CurrencyMismatchException();
                    }
                    return new Money(this.amount.add(other.amount), this.currency);
                }
            }
            
            public record OrderId(UUID value) {
                public static OrderId generate() {
                    return new OrderId(UUID.randomUUID());
                }
            }
            ```
            
            ### Servicios de Dominio
            
            Lógica de negocio que no pertenece naturalmente a una entidad.
            
            ```java
            public class PricingService {
                
                public Money calculatePrice(Product product, Customer customer) {
                    Money basePrice = product.getBasePrice();
                    Discount discount = determineDiscount(customer);
                    return discount.apply(basePrice);
                }
            }
            ```
            
            ### Eventos de Dominio
            
            Representan algo significativo que ocurrió en el dominio.
            
            ```java
            public record OrderConfirmedEvent(
                OrderId orderId,
                CustomerId customerId,
                Money total,
                LocalDateTime occurredAt
            ) implements DomainEvent {}
            ```
            
            ## Reglas del Dominio
            
            1. **Sin dependencias externas**: Solo Java puro (y librerías de utilidad)
            2. **Inmutabilidad cuando sea posible**: Especialmente Value Objects
            3. **Validación en constructores**: Nunca crear objetos inválidos
            4. **Ubiquitous Language**: Usar el lenguaje del negocio
            """;

        return new DocumentData(content, Map.of(
                "source", "Hexagonal Architecture - Domain",
                "topic", "Dominio en Arquitectura Hexagonal",
                "category", "Architecture"
        ));
    }

    private static DocumentData getHexagonalApplicationDocument() {
        String content = """
            # Capa de Aplicación en Arquitectura Hexagonal
            
            La capa de aplicación contiene los casos de uso (Use Cases) que orquestan 
            el flujo de datos hacia y desde las entidades del dominio, y dirigen a 
            los puertos de salida para realizar operaciones.
            
            ## Casos de Uso
            
            Un caso de uso representa una acción específica que el sistema puede realizar.
            No contiene lógica de negocio, solo orquesta.
            
            ```java
            @Service
            @Transactional
            public class CreateOrderUseCase {
                
                private final OrderRepository orderRepository;
                private final CustomerRepository customerRepository;
                private final EventPublisher eventPublisher;
                private final PricingService pricingService;
                
                public CreateOrderUseCase(
                        OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        EventPublisher eventPublisher,
                        PricingService pricingService) {
                    this.orderRepository = orderRepository;
                    this.customerRepository = customerRepository;
                    this.eventPublisher = eventPublisher;
                    this.pricingService = pricingService;
                }
                
                public OrderId execute(CreateOrderCommand command) {
                    // 1. Obtener datos necesarios
                    Customer customer = customerRepository.findById(command.customerId())
                        .orElseThrow(() -> new CustomerNotFoundException(command.customerId()));
                    
                    // 2. Crear la entidad de dominio
                    Order order = Order.create(customer.getId());
                    
                    // 3. Ejecutar lógica de dominio
                    for (var item : command.items()) {
                        Product product = productRepository.findById(item.productId())
                            .orElseThrow(() -> new ProductNotFoundException(item.productId()));
                        order.addLine(product, item.quantity());
                    }
                    
                    // 4. Persistir
                    orderRepository.save(order);
                    
                    // 5. Publicar eventos
                    eventPublisher.publish(new OrderCreatedEvent(order.getId()));
                    
                    return order.getId();
                }
            }
            ```
            
            ## Commands y Queries (CQRS simplificado)
            
            ```java
            // Command - Representa una intención de cambiar estado
            public record CreateOrderCommand(
                CustomerId customerId,
                List<OrderItemCommand> items,
                ShippingAddress shippingAddress
            ) {}
            
            // Query - Representa una solicitud de información
            public record GetOrderQuery(OrderId orderId) {}
            
            // Use Case de consulta
            @Service
            @Transactional(readOnly = true)
            public class GetOrderUseCase {
                
                private final OrderRepository orderRepository;
                
                public OrderDTO execute(GetOrderQuery query) {
                    return orderRepository.findById(query.orderId())
                        .map(this::toDTO)
                        .orElseThrow(() -> new OrderNotFoundException(query.orderId()));
                }
            }
            ```
            
            ## Responsabilidades de la Capa de Aplicación
            
            1. **Orquestación**: Coordina la ejecución del caso de uso
            2. **Transacciones**: Define los límites transaccionales
            3. **Autorización**: Verifica permisos (puede delegarse)
            4. **Validación de entrada**: Valida comandos antes de pasarlos al dominio
            5. **Mapeo**: Convierte entre DTOs y entidades de dominio
            
            ## Lo que NO debe hacer
            
            - Contener lógica de negocio (eso va en el dominio)
            - Conocer detalles de infraestructura (HTTP, SQL, etc.)
            - Tener dependencias de frameworks específicos en su firma
            """;

        return new DocumentData(content, Map.of(
                "source", "Hexagonal Architecture - Application",
                "topic", "Capa de Aplicación",
                "category", "Architecture"
        ));
    }

    // ==================== Clean Architecture ====================

    private static DocumentData getCleanArchitectureOverviewDocument() {
        String content = """
            # Clean Architecture
            
            Clean Architecture es un patrón arquitectónico propuesto por Robert C. Martin 
            (Uncle Bob) que organiza el código en capas concéntricas, donde las dependencias 
            apuntan hacia adentro.
            
            ## La Regla de Dependencia
            
            "Las dependencias del código fuente solo pueden apuntar hacia adentro."
            
            Esto significa que:
            - El dominio no conoce nada del mundo exterior
            - Los casos de uso conocen el dominio pero no la infraestructura
            - La infraestructura conoce todo pero nadie la conoce a ella
            
            ## Las Capas
            
            ```
            ┌─────────────────────────────────────────┐
            │          Frameworks & Drivers           │  ← Infrastructure
            │  ┌─────────────────────────────────┐    │
            │  │      Interface Adapters         │    │  ← Controllers, Presenters
            │  │  ┌─────────────────────────┐    │    │
            │  │  │     Application         │    │    │  ← Use Cases
            │  │  │  ┌─────────────────┐    │    │    │
            │  │  │  │    Entities     │    │    │    │  ← Domain
            │  │  │  └─────────────────┘    │    │    │
            │  │  └─────────────────────────┘    │    │
            │  └─────────────────────────────────┘    │
            └─────────────────────────────────────────┘
            ```
            
            ## Relación con Arquitectura Hexagonal
            
            Clean Architecture y Arquitectura Hexagonal comparten los mismos principios:
            
            | Hexagonal | Clean Architecture |
            |-----------|-------------------|
            | Domain | Entities |
            | Ports (Input) | Use Case Boundaries |
            | Ports (Output) | Gateway Interfaces |
            | Adapters | Controllers, Presenters, Gateways |
            
            ## Beneficios
            
            1. **Independencia de Frameworks**: El negocio no depende de Spring, Hibernate, etc.
            2. **Testeable**: La lógica de negocio se prueba sin UI, DB, servidores
            3. **Independencia de UI**: La UI puede cambiar sin afectar el negocio
            4. **Independencia de BD**: Puedes cambiar Oracle por MongoDB
            5. **Independencia de agentes externos**: Las reglas de negocio no saben del exterior
            """;

        return new DocumentData(content, Map.of(
                "source", "Clean Architecture - Overview",
                "topic", "Clean Architecture",
                "category", "Architecture"
        ));
    }

    private static DocumentData getCleanArchitectureLayersDocument() {
        String content = """
            # Capas de Clean Architecture en Java
            
            ## Estructura de Paquetes Recomendada
            
            ```
            com.company.project/
            ├── domain/                    # Capa de Entidades
            │   ├── model/                 # Entidades y Value Objects
            │   ├── service/               # Servicios de Dominio
            │   └── exception/             # Excepciones de Dominio
            │
            ├── application/               # Capa de Casos de Uso
            │   ├── port/
            │   │   ├── input/             # Interfaces de casos de uso
            │   │   └── output/            # Interfaces de repositorios/gateways
            │   ├── service/               # Implementaciones de casos de uso
            │   └── dto/                   # Data Transfer Objects
            │
            └── infrastructure/            # Capa de Frameworks & Drivers
                ├── config/                # Configuración de Spring
                ├── persistence/           # JPA, MongoDB, etc.
                │   ├── entity/            # Entidades de BD
                │   ├── repository/        # Repositorios Spring Data
                │   └── mapper/            # Mappers Entity <-> Domain
                ├── web/                   # Controladores REST
                │   ├── controller/
                │   ├── dto/               # Request/Response DTOs
                │   └── mapper/
                └── external/              # Clientes de APIs externas
            ```
            
            ## Flujo de Datos
            
            ```
            HTTP Request
                 │
                 ▼
            ┌─────────────────┐
            │   Controller    │  Traduce HTTP → Command/Query
            └────────┬────────┘
                     │
                     ▼
            ┌─────────────────┐
            │    Use Case     │  Orquesta el flujo
            └────────┬────────┘
                     │
                     ▼
            ┌─────────────────┐
            │    Domain       │  Ejecuta lógica de negocio
            └────────┬────────┘
                     │
                     ▼
            ┌─────────────────┐
            │   Repository    │  Persiste/Recupera datos
            └─────────────────┘
            ```
            
            ## Mapeo entre Capas
            
            Cada capa tiene sus propios modelos de datos:
            
            ```java
            // Web Layer DTO
            public record CreateUserRequest(String email, String name) {}
            
            // Application Layer DTO
            public record CreateUserCommand(Email email, UserName name) {}
            
            // Domain Entity
            public class User {
                private UserId id;
                private Email email;
                private UserName name;
            }
            
            // Persistence Entity
            @Entity
            public class UserEntity {
                @Id private UUID id;
                private String email;
                private String name;
            }
            ```
            
            Mappers para convertir entre capas:
            
            ```java
            @Component
            public class UserMapper {
                
                public CreateUserCommand toCommand(CreateUserRequest request) {
                    return new CreateUserCommand(
                        new Email(request.email()),
                        new UserName(request.name())
                    );
                }
                
                public User toDomain(UserEntity entity) {
                    return User.reconstitute(
                        new UserId(entity.getId()),
                        new Email(entity.getEmail()),
                        new UserName(entity.getName())
                    );
                }
                
                public UserEntity toEntity(User user) {
                    UserEntity entity = new UserEntity();
                    entity.setId(user.getId().value());
                    entity.setEmail(user.getEmail().value());
                    entity.setName(user.getName().value());
                    return entity;
                }
            }
            ```
            
            ## Testing por Capas
            
            - **Domain**: Tests unitarios puros, sin mocks
            - **Application**: Tests unitarios con mocks de puertos de salida
            - **Infrastructure**: Tests de integración con BD real o TestContainers
            - **E2E**: Tests de integración completos con MockMvc
            """;

        return new DocumentData(content, Map.of(
                "source", "Clean Architecture - Layers",
                "topic", "Capas de Clean Architecture",
                "category", "Architecture"
        ));
    }

    private static DocumentData getPracticalExamplesDocument() {
        String content = """
            # Ejemplos Prácticos: SOLID + Arquitectura Hexagonal
            
            ## Ejemplo Completo: Sistema de Órdenes
            
            ### 1. Dominio (Entidades y Value Objects)
            
            ```java
            // Value Object
            public record OrderId(UUID value) {
                public static OrderId generate() {
                    return new OrderId(UUID.randomUUID());
                }
            }
            
            // Entidad
            public class Order {
                private final OrderId id;
                private final List<OrderLine> lines = new ArrayList<>();
                private OrderStatus status = OrderStatus.DRAFT;
                
                private Order(OrderId id) {
                    this.id = id;
                }
                
                public static Order create() {
                    return new Order(OrderId.generate());
                }
                
                // Lógica de negocio
                public void addProduct(Product product, int quantity) {
                    validateCanModify();
                    lines.add(new OrderLine(product, quantity));
                }
                
                public void confirm() {
                    if (lines.isEmpty()) {
                        throw new EmptyOrderException(id);
                    }
                    this.status = OrderStatus.CONFIRMED;
                }
                
                private void validateCanModify() {
                    if (status != OrderStatus.DRAFT) {
                        throw new OrderNotModifiableException(id);
                    }
                }
            }
            ```
            
            ### 2. Puertos
            
            ```java
            // Puerto de entrada (caso de uso)
            public interface CreateOrderUseCase {
                OrderId execute(CreateOrderCommand command);
            }
            
            // Puerto de salida (repositorio)
            public interface OrderRepository {
                void save(Order order);
                Optional<Order> findById(OrderId id);
            }
            ```
            
            ### 3. Caso de Uso (Aplicación)
            
            ```java
            @Service
            public class CreateOrderService implements CreateOrderUseCase {
                
                private final OrderRepository orderRepository;
                private final ProductRepository productRepository;
                
                @Override
                @Transactional
                public OrderId execute(CreateOrderCommand command) {
                    Order order = Order.create();
                    
                    for (var item : command.items()) {
                        Product product = productRepository.findById(item.productId())
                            .orElseThrow(() -> new ProductNotFoundException(item.productId()));
                        order.addProduct(product, item.quantity());
                    }
                    
                    order.confirm();
                    orderRepository.save(order);
                    
                    return order.getId();
                }
            }
            ```
            
            ### 4. Adaptadores
            
            ```java
            // Adaptador de entrada (REST Controller)
            @RestController
            @RequestMapping("/api/orders")
            public class OrderController {
                
                private final CreateOrderUseCase createOrderUseCase;
                
                @PostMapping
                public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
                    CreateOrderCommand command = toCommand(request);
                    OrderId orderId = createOrderUseCase.execute(command);
                    return ResponseEntity.ok(new OrderResponse(orderId.value().toString()));
                }
            }
            
            // Adaptador de salida (JPA Repository)
            @Repository
            public class JpaOrderRepository implements OrderRepository {
                
                private final SpringDataOrderRepository springData;
                private final OrderEntityMapper mapper;
                
                @Override
                public void save(Order order) {
                    OrderEntity entity = mapper.toEntity(order);
                    springData.save(entity);
                }
                
                @Override
                public Optional<Order> findById(OrderId id) {
                    return springData.findById(id.value())
                        .map(mapper::toDomain);
                }
            }
            ```
            
            ## Principios SOLID Aplicados
            
            - **SRP**: Cada clase tiene una responsabilidad clara
            - **OCP**: Nuevos adaptadores sin modificar casos de uso
            - **LSP**: Cualquier repositorio que implemente la interfaz funciona
            - **ISP**: Interfaces pequeñas y específicas
            - **DIP**: Casos de uso dependen de abstracciones, no de JPA/HTTP
            """;

        return new DocumentData(content, Map.of(
                "source", "Practical Examples",
                "topic", "Ejemplos Prácticos SOLID y Hexagonal",
                "category", "Examples"
        ));
    }
}
