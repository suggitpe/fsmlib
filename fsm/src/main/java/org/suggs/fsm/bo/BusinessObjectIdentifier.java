package org.suggs.fsm.bo;

import java.util.Objects;

/**
 * Bean type class to encapsulate all values that make up the
 * identifiable characteristics of a business object - not to be used
 * as a composite id within O/R mappers.
 */
public class BusinessObjectIdentifier {

    public static final int NUM_BUCKETS = 1000;

    private String internalType_;
    private String type_;
    private String id_;
    private String externalId_;
    private String owner_;
    private int version_ = 0;
    // -2 value means it's has not been computed
    private int hash_ = -2;

    /**
     * Constructs a new instance based on the XSD type instance.
     */
//    public BusinessObjectIdentifier(ScribeBusinessObjectIdentifier xmlBoId) {
//        super();
//        if (xmlBoId.getLinguaBusObjType() != null) {
//            setType(xmlBoId.getLinguaBusObjType().toString());
//        }
//        setInternalType(xmlBoId.getScribeBusObjType().toString());
//
//        setId(xmlBoId.getScribeBusObjId());
//        setExternalId(xmlBoId.getLinguaBusObjId());
//        setOwner(xmlBoId.getBusObjOwner());
//        if (xmlBoId.getBusObjVersion() != null) {
//            setVersion(xmlBoId.getBusObjVersion().intValue());
//        }
//        // we will ignore the hash value for the time being, assuming
//        // that the setting of the id will set the hash correctly
//    }

    /**
     * Constructs a new instance using all specified values (the hash
     * will be computed).
     */
    public BusinessObjectIdentifier(String internalType, String type, String id, String owner, int version) {
        this(internalType, type, id, owner, version, null);
    }

    /**
     * Constructs a new instance using all specified values (the hash
     * will be computed).
     */
    public BusinessObjectIdentifier(String internalType, String type, String id, String owner, int version, String externalId) {
        super();
        setInternalType(internalType);
        setType(type);
        setId(id);
        setOwner(owner);
        setVersion(version);
        setExternalId(externalId);
    }

    /**
     * Copy constructor
     */
    public BusinessObjectIdentifier(BusinessObjectIdentifier boId) {
        super();
        setType(boId.getType());
        setId(boId.getId());
        setExternalId(boId.getExternalId());
        setOwner(boId.getOwner());
        setVersion(boId.getVersion());
    }

    /**
     * No-args contructor for the business objects to use when created
     * by the ORM.
     */
    BusinessObjectIdentifier() {
        super();
    }

    /**
     * Returns the bucket number an event was delivered to or bound
     * for - must be positive between 0 and MAX_BUCKET_NUMBER
     * inclusive.
     * <p>
     * If the id_ is set, will be computed based on it. If the id is
     * not set, will be computed based on the external id.
     *
     * @return The bucket number an event was delivered to.
     */
    public int getHash() {
        if (hash_ != -2) {
            return hash_;
        }
        if (id_ != null) {
            hash_ = linguaHashFromId(id_);
        } else if (externalId_ != null) {
            hash_ = linguaHashFromId(externalId_);
        } else {
            hash_ = -1;
        }

        return hash_;
    }

    /**
     * Returns the business object id an event specifically relates to -
     * may be <code>null</code> if the business object has not been
     * created yet. Note, that this is the scribe internal business
     * object id, which is usually auto generated by scribe. It also
     * differs from Lingua business object id.
     *
     * @hibernate.column name="bo_id"
     */
    public String getId() {
        return id_;
    }

    /**
     * Sets the id field to the specified value.
     *
     * @param id The id to set.
     */
    public void setId(String id) {
        id_ = id;
    }

    /**
     * Returns the external business object id. For example, the
     * Lingua business object id. May me <code>null</code> if it is
     * an internal business object.
     *
     * @hibernate.property column="ext_bo_id"
     */
    public String getExternalId() {
        return externalId_;
    }

    /**
     * Sets the id field to the specified value.
     */
    public void setExternalId(String externalId) {
        externalId_ = externalId;
    }

    /**
     * Returns the system owner of the business object an event
     * specifically relates to - may not be null.
     *
     * @return The system owner of the business object an event
     * specifically relates to - may not be null.
     * @hibernate.property column="bo_owner"
     */
    public String getOwner() {
        return owner_;
    }

    /**
     * Sets the owner field to the specified value.
     *
     * @param owner The owner to set.
     */
    public void setOwner(String owner) {
        owner_ = owner;
    }

    /**
     * Returns the business object type an event specifically relates
     * to - may never be null and must always have a value.
     *
     * @return The business object type an event specifically relates
     * to - may never be null and must always have a value.
     * @hibernate.property column="bo_type"
     */
    public String getType() {
        return type_;
    }

    /**
     * Sets the type field to the specified value.
     *
     * @param type The type to set.
     */
    public void setType(String type) {
        type_ = type;
    }

    /**
     * Returns the value of internalType.
     *
     * @return Returns the internalType.
     */
    public String getInternalType() {
        return internalType_;
    }

    /**
     * Sets the internalType field to the specified value.
     *
     * @param internalType The internalType to set.
     */
    public void setInternalType(String internalType) {
        assert internalType != null;
        internalType_ = internalType;
    }

    /**
     * Returns the business object version an event specifically
     * relates to - may be -ve if the business object has not been
     * created yet.
     *
     * @return The business object version an event specifically
     * relates to - may be -ve if the business object has not
     * been created yet.
     * @hibernate.property column="bo_version"
     */
    public int getVersion() {
        return version_;
    }

    /**
     * Sets the version field to the specified value.
     *
     * @param version The version to set.
     */
    public void setVersion(int version) {
        version_ = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessObjectIdentifier that = (BusinessObjectIdentifier) o;
        return version_ == that.version_ &&
                hash_ == that.hash_ &&
                Objects.equals(internalType_, that.internalType_) &&
                Objects.equals(type_, that.type_) &&
                Objects.equals(id_, that.id_) &&
                Objects.equals(externalId_, that.externalId_) &&
                Objects.equals(owner_, that.owner_);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalType_, type_, id_, externalId_, owner_, version_, hash_);
    }

    @Override
    public String toString() {
        return "BusinessObjectIdentifier{" +
                "internalType_='" + internalType_ + '\'' +
                ", type_='" + type_ + '\'' +
                ", id_='" + id_ + '\'' +
                ", externalId_='" + externalId_ + '\'' +
                ", owner_='" + owner_ + '\'' +
                ", version_=" + version_ +
                ", hash_=" + hash_ +
                '}';
    }

    /**
     * Generate a standard Lingua "bucket ID" hash value from a
     * business object ID
     *
     * @return the Lingua hash value for the ID (>= 0 && <= 999)
     * @id the ID to generate the hash from (may not be null)
     */
    int linguaHashFromId(String id) {
        assert id != null;

        int sum = 0;
        for (int i = 0; i < id.length(); i++) {
            sum += id.charAt(i);
        }

        return sum % NUM_BUCKETS;
    }

}
