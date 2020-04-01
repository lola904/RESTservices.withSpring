package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

@RestController
class OrderController {

  private final OrderRepository orderRepository;
  private final OrderModelAssembler assembler;

  OrderController(OrderRepository orderRepository,
                    OrderModelAssembler assembler) {

    this.orderRepository = orderRepository;
    this.assembler = assembler;
  }

  @GetMapping("/orders")
  CollectionModel<EntityModel<Order>> all() {

    List<EntityModel<Order>> orders = orderRepository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return new CollectionModel<>(orders,
      linkTo(methodOn(OrderController.class).all()).withSelfRel());
  }

  @GetMapping("/orders/{id}")
  EntityModel<Order> one(@PathVariable Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));

    return assembler.toModel(order);
  }

  @PostMapping("/orders")
  ResponseEntity<EntityModel<Order>> newOrder(@RequestBody Order order) {

    order.setStatus(Status.IN_PROGRESS);
    Order newOrder = orderRepository.save(order);

    return ResponseEntity
      .created(linkTo(methodOn(OrderController.class).one(newOrder.getId())).toUri())
      .body(assembler.toModel(newOrder));
  }
}