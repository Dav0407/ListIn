# Database Design Documentation

## Overview

This database is designed to support a publication system where users can create posts under specific categories. Each category may have unique attributes, making this system flexible and adaptable for various publication types. All primary keys use UUIDs for enhanced uniqueness and distributed systems support.

## Table Descriptions with Purpose and Key Differences

### 1. Users Table

**Purpose**: Stores user account details for individuals interacting with the application, either as sellers or reviewers.

| Column                  | Type       | Description                                           |
|-------------------------|------------|-------------------------------------------------------|
| user_id (PK)            | UUID       | Unique identifier for each user.                      |
| first_name              | VARCHAR    | User’s first name.                                    |
| last_name               | VARCHAR    | User’s last name.                                     |
| email                   | VARCHAR    | User’s email (used for login, indexed for performance). |
| password                | VARCHAR    | Encrypted password for user authentication.           |
| role                    | VARCHAR    | User’s role (e.g., admin, seller).                    |
| profile_image_path      | VARCHAR    | Path to the user’s profile image.                     |
| business_name           | VARCHAR    | Business name, if applicable.                         |
| contact_information     | TEXT       | User’s contact information.                           |
| rating                  | DECIMAL    | Average rating based on user’s reviews.               |
| address                 | TEXT       | User’s address details.                               |
| date_created            | TIMESTAMP  | Date and time the user was created.                   |

**Relationships**:
- One-to-Many with `publications`: A user (seller) can create multiple publications.
- One-to-Many with `reviews`: Users can leave multiple reviews on different publications.

---

### 2. Publications Table

**Purpose**: Stores details of each user-generated publication, representing an item for sale or rent.

| Column                  | Type       | Description                                          |
|-------------------------|------------|------------------------------------------------------|
| publication_id (PK)     | UUID       | Unique identifier for each publication.              |
| title                   | VARCHAR    | Title of the publication.                            |
| description             | TEXT       | Detailed description of the publication.             |
| price                   | DECIMAL    | Price of the item or service in the publication.     |
| stock_quantity          | INT        | Number of items available.                           |
| date_posted             | TIMESTAMP  | Date when the publication was created.               |
| date_updated            | TIMESTAMP  | Last update date for the publication.                |
| category_id (FK)        | UUID       | Foreign key to `categories`.                         |
| condition_id (FK)       | UUID       | Foreign key to `product_conditions`.                 |
| seller_id (FK)          | UUID       | Foreign key to `users`.                              |
| publication_type_id (FK)| UUID       | Foreign key to `publication_types`.                  |
| publication_status_id (FK) | UUID    | Foreign key to `publication_statuses`.               |

**Relationships**:
- Many-to-One with `users`: Each publication is created by a single user (seller).
- Many-to-One with `categories`: Each publication belongs to one category.
- One-to-Many with `publication_images`, `publication_attribute_values`, and `reviews`.

---

### 3. Categories Table

**Purpose**: Organizes publications into hierarchical groups with unique attributes based on the category.

| Column             | Type    | Description                                     |
|--------------------|---------|-------------------------------------------------|
| category_id (PK)   | UUID    | Unique identifier for each category.            |
| category_name      | VARCHAR | Name of the category.                           |
| description        | TEXT    | Description of the category.                    |
| parent_id (FK)     | UUID    | Self-referencing foreign key for nested categories. |

**Relationships**:
- One-to-Many (Self-Referencing) for category hierarchy.
- Many-to-Many with `attribute_keys` through `category_attributes`.

---

### 4. Category Attributes Table

**Purpose**: Links `categories` with `attribute_keys`, defining which attributes are relevant to each category.

| Column                  | Type    | Description                                        |
|-------------------------|---------|----------------------------------------------------|
| category_attribute_id (PK) | UUID | Unique identifier for each category-attribute link. |
| category_id (FK)        | UUID    | Foreign key to `categories`.                       |
| attribute_id (FK)       | UUID    | Foreign key to `attribute_keys`.                   |

**Relationships**:
- Many-to-Many between `categories` and `attribute_keys`.

---

### 5. Publication Attribute Values Table

**Purpose**: Stores the actual attribute values assigned to each publication based on category-specific attributes.

| Column                       | Type    | Description                                      |
|------------------------------|---------|--------------------------------------------------|
| publication_attribute_value_id (PK) | UUID | Unique identifier for each attribute value entry. |
| publication_id (FK)          | UUID    | Foreign key to `publications`.                   |
| category_attribute_id (FK)   | UUID    | Foreign key to `category_attributes`.            |
| value                        | VARCHAR | The actual value of the attribute for this publication. |

**Key Difference from `attribute_values`**: Stores **specific attribute values** for each publication instance, whereas `attribute_values` holds **predefined options** for an attribute.

---

### 6. Attribute Keys Table

**Purpose**: Defines attributes assignable to categories, like "Color" or "Size".

| Column             | Type    | Description                                         |
|--------------------|---------|-----------------------------------------------------|
| attribute_id (PK)  | UUID    | Unique identifier for each attribute.               |
| name               | VARCHAR | Name of the attribute.                              |
| data_type          | VARCHAR | Data type of the attribute (e.g., String, Integer). |

**Relationships**:
- One-to-Many with `category_attributes`: Defines attributes available to categories.

---

### 7. Attribute Values Table (Optional)

**Purpose**: Stores predefined values for attributes (e.g., "Red" or "Blue" for "Color").

| Column             | Type    | Description                                         |
|--------------------|---------|-----------------------------------------------------|
| attribute_value_id (PK) | UUID | Unique identifier for each attribute value.      |
| value              | VARCHAR | Allowed value for the attribute.                   |
| attribute_id (FK)  | UUID    | Foreign key to `attribute_keys`.                   |

---

### 8. Product Conditions Table

**Purpose**: Provides possible conditions for publications, like "New", "Used".

| Column           | Type    | Description                                  |
|------------------|---------|----------------------------------------------|
| condition_id (PK)| UUID    | Unique identifier for each condition.        |
| condition_name   | VARCHAR | Name of the condition.                       |
| description      | TEXT    | Detailed description of the condition.       |

---

### 9. Publication Types Table

**Purpose**: Defines types of publications, e.g., "Sale" or "Rental".

| Column                   | Type    | Description                                  |
|--------------------------|---------|----------------------------------------------|
| publication_type_id (PK) | UUID    | Unique identifier for each type.             |
| type_name                | VARCHAR | Type name for the publication.               |
| description              | TEXT    | Description of the publication type.         |

---

### 10. Publication Statuses Table

**Purpose**: Tracks publication status, e.g., "Active" or "Sold".

| Column                    | Type    | Description                                    |
|---------------------------|---------|------------------------------------------------|
| publication_status_id (PK)| UUID    | Unique identifier for each status.             |
| status_name               | VARCHAR | Status name of the publication.                |
| description               | TEXT    | Additional description of the status.          |

---

### 11. Publication Images Table

**Purpose**: Stores images associated with publications.

| Column            | Type    | Description                                     |
|-------------------|---------|-------------------------------------------------|
| image_id (PK)     | UUID    | Unique identifier for each image.               |
| image_url         | VARCHAR | URL of the image.                               |
| is_primary        | BOOLEAN | Indicates if this is the primary image.         |
| publication_id (FK) | UUID  | Foreign key to `publications`.                  |

---

### 12. Reviews Table

**Purpose**: Stores reviews left by users on publications.

| Column           | Type       | Description                                      |
|------------------|------------|--------------------------------------------------|
| review_id (PK)   | UUID       | Unique identifier for each review.               |
| rating           | INT        | Numeric rating given by the user.                |
| comment          | TEXT       | Text of the review.                              |
| date_created     | TIMESTAMP  | Date of review creation.                         |
| publication_id (FK) | UUID    | Foreign key to `publications`.                   |
| user_id (FK)     | UUID       | Foreign key to `users`.                          |

---

## Indexing Recommendations

To improve query performance:
- Index `publication_id` in `publication_attribute_values`, `publication_images`, and `reviews`.
- Index `category_id` in `category_attributes`.
- Index `user_id` and `email` in `users` (unique index on `email`).

---

## Conclusion

This design supports a scalable and dynamic publication system with unique attributes by category. UUIDs for primary keys enable compatibility with distributed systems. This documentation provides a comprehensive guide for developers and stakeholders.
