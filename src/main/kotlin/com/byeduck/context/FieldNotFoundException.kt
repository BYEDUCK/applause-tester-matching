package com.byeduck.context

class FieldNotFoundException(fieldName: String) : IllegalStateException("Field $fieldName not found")