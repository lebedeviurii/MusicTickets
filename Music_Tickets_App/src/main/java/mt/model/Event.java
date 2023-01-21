package mt.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "findEventByPost", query = "SELECT e from Event e WHERE :post MEMBER OF e.posts")
public class Event {
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Id
        @Column(name = "id")
        private Long id;
        @Basic
        @Column(name = "start_date", unique = true, columnDefinition = "TIMESTAMP")
        private LocalDateTime startDate;
        @Basic
        @Column(name = "end_date", columnDefinition = "TIMESTAMP")
        private LocalDateTime endDate;
        @Basic
        @Column(name = "place")
        private String place;
        @Basic
        @Column(name = "name", unique = true)
        private String name;
        @Basic
        @Column(name = "active_for_selling")
        private boolean activeForSelling;
        @Basic
        @Column(name = "selling_start_date")
        private LocalDateTime sellingStartDate;

        @ManyToMany
        @OrderBy("id")
        private List<Band> bands;
        @OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
        @OrderBy("id")
        private List<Post> posts;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public LocalDateTime getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
                this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
                this.endDate = endDate;
        }

        public String getPlace() {
                return place;
        }

        public void setPlace(String place) {
                this.place = place;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public boolean isActiveForSelling() {
                return activeForSelling;
        }

        public void setActiveForSelling(boolean activeForSelling) {
                this.activeForSelling = activeForSelling;
        }

        public LocalDateTime getSellingStartDate() {
                return sellingStartDate;
        }

        public void setSellingStartDate(LocalDateTime sellingStartDate) {
                this.sellingStartDate = sellingStartDate;
        }

        public List<Post> getPosts() {
                return posts;
        }

        public void setPosts(List<Post> posts) {
                this.posts = posts;
        }

        public List<Band> getBand() {return this.bands;}

        public Event() {
                this.activeForSelling = false;
                this.bands = new ArrayList<>();
                this.posts = new ArrayList<>();
        }

        public Event(String name, String place,
                     LocalDateTime sellingStartDate, LocalDateTime startDate, LocalDateTime endDate, List<Band> bands){
                this.name = name;
                this.place = place;
                this.startDate = startDate;
                this.endDate = endDate;
                this.sellingStartDate = sellingStartDate;
                if (bands != null){
                        this.bands = bands;
                }
                this.posts = null;
        }

        public void addBand(Band band){
                Objects.requireNonNull(band);
                if (bands == null){
                        bands = new ArrayList<>();
                }
                bands.add(band);
        }

        public void removeBand(Band band) {
                Objects.requireNonNull(band);
                if (bands == null) {
                        return;
                }
                bands.removeIf(b -> Objects.equals(b.getId(), band.getId()));
        }

        public void addPost(Post post){
                Objects.requireNonNull(post);
                if (posts == null){
                        posts = new ArrayList<>();
                }
                posts.add(post);
        }

        public void removePost(Post post) {
                Objects.requireNonNull(post);
                if (posts == null) {
                        return;
                }
                posts.removeIf(p -> Objects.equals(p.getId(), post.getId()));
        }
}
