CREATE TABLE organizations (
    id UUID PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_organizations_name
        CHECK (length(trim(name)) > 0)
);

CREATE TABLE equipment (
    id UUID PRIMARY KEY,
    organization_id UUID NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_equipment_organization
        FOREIGN KEY (organization_id)
        REFERENCES organizations (id),

    CONSTRAINT uq_equipment_organization_code
        UNIQUE (organization_id, code),

    CONSTRAINT chk_equipment_code
        CHECK (code ~ '^[A-Z0-9][A-Z0-9_-]{0,49}$'),

    CONSTRAINT chk_equipment_name
        CHECK (length(trim(name)) > 0)
);